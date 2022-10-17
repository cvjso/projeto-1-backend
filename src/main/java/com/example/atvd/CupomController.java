package com.example.atvd;

import com.example.atvd.entities.Cupom;
import com.example.atvd.entities.Produto;
import com.example.atvd.repository.CupomRepository;
import com.example.atvd.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path="/cupom")
public class CupomController {
    @Autowired
    private CupomRepository cupomRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewCupom (@RequestParam Date dataVenda
            , @RequestParam String cpf) {
        Cupom n = new Cupom();
        n.setDataVenda(dataVenda);
        n.setCpf(cpf);
        n.setStatus("ATIVO");
        cupomRepository.save(n);
        return "Saved";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteCupom(@RequestParam long id) {
        cupomRepository.deleteById(id);
        return "Removed";
    }

    @GetMapping(path="/get")
    public @ResponseBody Optional<Cupom> getCupom(@RequestParam long id) {
        return cupomRepository.findById(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateCupom(@RequestParam long id, @RequestParam Date dataVenda, @RequestParam String cpf, @RequestParam String status) {
        Cupom n = new Cupom();
        n.setId(id);
        n.setDataVenda(dataVenda);
        n.setCpf(cpf);
        n.setStatus(status);
        cupomRepository.save(n);
        return "Updated";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Cupom> getAllCupom() {
        return cupomRepository.findAll();
    }
}
