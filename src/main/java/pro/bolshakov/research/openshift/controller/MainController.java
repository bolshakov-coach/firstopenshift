package pro.bolshakov.research.openshift.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Value("${test.var.message:not_found}")
    private String testText;

    @GetMapping("")
    public String index(){
        return "It is working. Value from test.var.message -> " + testText;
    }

}
