package com.cesar.school.projeto1cvjsophmt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ProdutoController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/produto")
    public Produto produto(@RequestParam(value = "name", defaultValue = "padrão") String name) {
        return new Produto(counter.incrementAndGet(), name, 0, 0);
    }
}
