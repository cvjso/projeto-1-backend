package com.example.atvd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestProdutoController {

    @GetMapping("/teste")
    public @ResponseBody String getAllUsers() {
        return "hello";
    }
}
