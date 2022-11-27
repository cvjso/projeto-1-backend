package com.example.atvd;

import com.example.atvd.entities.Cupom;
import com.example.atvd.entities.Produto;
import com.example.atvd.entities.Venda;
import com.example.atvd.repository.CupomRepository;
import com.example.atvd.repository.ProdutoRepository;
import com.example.atvd.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.example.atvd.CupomController.downloadUsingStream;

@Controller
@RequestMapping(path="/venda")
public class VendaController {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CupomRepository cupomRepository;

    @PutMapping(path = "/import")
    public @ResponseBody String importXML(@RequestParam String path) throws ParseException {

        String destination = "/home/carlosvinicius/cesar/projeto-1-backend/atvd/src/main/java/com/example/atvd/XMLS/venda.xml";

        try {
            downloadUsingStream(path, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadXML reader = new ReadXML();
        ArrayList<String> xmlFields = reader.run(destination, Venda.getFields());

        Venda n = new Venda();
        n.setId(Long.parseLong(xmlFields.get(0)));
        n.setProdutoId(Long.parseLong(xmlFields.get(1)));
        n.setQuantidade(Integer.valueOf(xmlFields.get(2)));
        n.setPrecoFinal(Integer.valueOf(xmlFields.get(3)));
        n.setImpostoPago(Integer.valueOf(xmlFields.get(4)));
        n.setCupomId(Long.parseLong(xmlFields.get(5)));
        vendaRepository.save(n);
        return "Imported";
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewVenda (@RequestParam long produtoId
            , @RequestParam Integer quantidade, @RequestParam Optional<Long> cupomId) {
        Venda n = new Venda();
        Optional<Produto> p = produtoRepository.findById(produtoId);
        if (cupomId.isPresent()) {
            Optional<Cupom> c = cupomRepository.findById(cupomId.get());
            if(c.isPresent()){
                n.setCupomId(cupomId.get());
            }
        }

        n.setProdutoId(produtoId);
        n.setQuantidade(quantidade);
        n.setPrecoFinal(quantidade * p.get().getPreco());
        n.setImpostoPago((int) (n.getPrecoFinal() * p.get().getAliquota()));
        vendaRepository.save(n);
        return "Saved";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteVenda(@RequestParam long id) {
        vendaRepository.deleteById(id);
        return "Removed";
    }

    @GetMapping(path="/get")
    public @ResponseBody Optional<Venda> getVenda(@RequestParam long id) {
        return vendaRepository.findById(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateVenda (@RequestParam long id, @RequestParam long produtoId
            , @RequestParam Integer quantidade, @RequestParam long cupomId) {

        Optional<Produto> p = produtoRepository.findById(produtoId);
        Venda n = new Venda();
        n.setId(id);
        n.setProdutoId(produtoId);
        n.setQuantidade(quantidade);
        n.setCupomId(cupomId);
        n.setPrecoFinal(quantidade * p.get().getPreco());
        n.setImpostoPago((int) (n.getPrecoFinal() * p.get().getAliquota()));
        vendaRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Venda> getAllVenda() {
        return vendaRepository.findAll();
    }

    @GetMapping(path="/getByCupom")
    public @ResponseBody Object getAllVendaByCupom(@RequestParam Long cupomId) {
        Optional<Cupom> p = cupomRepository.findById(cupomId);
        if(p.isPresent()){
            Cupom pp = p.get();
            Iterable<Venda> vendas = vendaRepository.findAll();
            List<Venda> n_vendas = new ArrayList<Venda>();
            for (Venda venda:
                 vendas) {
                if(cupomId.equals(venda.getCupomId())){
                    n_vendas.add(venda);
                }
            }
            ArrayList<Object> response = new ArrayList<>();
            response.add(pp);
            response.add(n_vendas);
            return response;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
