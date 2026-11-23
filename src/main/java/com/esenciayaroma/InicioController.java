package com.esenciayaroma;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/panel")
    public String mostrarPanel() {
        return "inicio";
    }
}
