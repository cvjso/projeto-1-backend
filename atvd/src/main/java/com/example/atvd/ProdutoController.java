package com.example.atvd;

import com.example.atvd.entities.Produto;
import com.example.atvd.entities.Venda;
import com.example.atvd.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.atvd.CupomController.downloadUsingStream;

@Controller
@RequestMapping(path="/produto")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @PutMapping(path = "/import")
    public @ResponseBody String importXML(@RequestParam String path) throws ParseException {

        String destination = "/home/carlosvinicius/cesar/projeto-1-backend/atvd/src/main/java/com/example/atvd/XMLS/produto.xml";

        try {
            downloadUsingStream(path, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadXML reader = new ReadXML();
        List<Serializable> resultReader = reader.run(destination, Produto.getFields());
        List<String> xmlFields = (List<String>) resultReader.get(0);
        Integer len = (Integer) resultReader.get(1);
        produtoRepository.deleteAll();

        for (int i = 0; i < len; i++) {
            Produto n = new Produto();
            n.setId(Long.parseLong(xmlFields.get(0)));
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            n.setNome(xmlFields.get(1));
            n.setPreco(Integer.valueOf(xmlFields.get(2)));
            n.setAliquota(Double.parseDouble(xmlFields.get(3)));
            n.setCodigo(Long.valueOf(xmlFields.get(4)));
            xmlFields.remove(0);
            xmlFields.remove(0);
            xmlFields.remove(0);
            xmlFields.remove(0);
            xmlFields.remove(0);
            produtoRepository.save(n);
        }

        return "Imported";
    }

    @GetMapping(path = "/export")
    public @ResponseBody String exportCSV(@RequestParam String path) throws IOException {
        String result = "";
        for (Produto v : produtoRepository.findAll()) {
            result += String.format("\"%d\",", v.getId());
            result += String.format("\"%s\",", v.getNome());
            result += String.format("\"%d\",", v.getPreco());
            result += String.format("\"%f\",", v.getAliquota());
            result += String.format("\"%d\"\n", v.getCodigo());
        }
        String url = WriteCSV.run("", result);
        return  url;
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewProduto (@RequestParam String nome
            , @RequestParam Integer preco, @RequestParam Integer aliquota, @RequestParam Long codigo) {

        if(preco > 0 && aliquota >= 0 && aliquota <= 1 && !nome.strip().equals("")) {
            Produto n = new Produto();
            n.setNome(nome);
            n.setPreco(preco);
            n.setAliquota(aliquota);
            n.setCodigo(codigo);
            produtoRepository.save(n);
            return "Saved";
        }
        return "";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteProduto(@RequestParam long id) {
        produtoRepository.deleteById(id);
        return "Removed";
    }

    @GetMapping(path="/get")
    public @ResponseBody Optional<Produto> getProduto(@RequestParam long id) {
        return produtoRepository.findById(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateProduto(@RequestParam long id, @RequestParam String nome, @RequestParam Integer preco, @RequestParam Integer aliquota, @RequestParam Long codigo) {
        Produto n = new Produto();
        n.setId(id);
        n.setNome(nome);
        n.setPreco(preco);
        n.setAliquota(aliquota);
        n.setCodigo(codigo);
        produtoRepository.save(n);
        return "Updated";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Produto> getAllProduto() {
        return produtoRepository.findAll();
    }
}
