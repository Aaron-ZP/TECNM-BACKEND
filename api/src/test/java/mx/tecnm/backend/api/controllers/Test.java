package mx.tecnm.backend.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/hello")
    public String helloword(){
        return "Hola API Rest";
    }

    

}
