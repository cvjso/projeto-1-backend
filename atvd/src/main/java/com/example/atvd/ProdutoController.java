package com.example.atvd;

import com.example.atvd.entities.Produto;
import com.example.atvd.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/produto")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

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
