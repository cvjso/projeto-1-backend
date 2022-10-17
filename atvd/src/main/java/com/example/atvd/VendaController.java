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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/venda")
public class VendaController {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CupomRepository cupomRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
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
