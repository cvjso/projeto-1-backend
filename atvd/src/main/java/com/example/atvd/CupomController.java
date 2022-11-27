package com.example.atvd;

import com.example.atvd.entities.Cupom;
import com.example.atvd.entities.Produto;
import com.example.atvd.repository.CupomRepository;
import com.example.atvd.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping(path="/cupom")
public class CupomController {
    @Autowired
    private CupomRepository cupomRepository;

    @PutMapping(path = "/import")
    public @ResponseBody String importXML(@RequestParam String path) throws ParseException {

        String destination = "/home/carlosvinicius/cesar/projeto-1-backend/atvd/src/main/java/com/example/atvd/XMLS/cupom.xml";

        try {
            downloadUsingStream(path, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadXML reader = new ReadXML();
        ArrayList<String> xmlFields = reader.run(destination, Cupom.getFields());

        Cupom n = new Cupom();
        n.setId(Long.parseLong(xmlFields.get(0)));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        n.setDataVenda(formatter.parse(xmlFields.get(1)));
        n.setCpf(xmlFields.get(2));
        n.setStatus(xmlFields.get(3));
        cupomRepository.save(n);
        return "Imported";
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }



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
