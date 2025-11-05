package mx.tecnm.backend.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")

public class Test {

    @GetMapping("/hello")
    public String helloword(){
        
        return "Hola API Rest";
    }

}
