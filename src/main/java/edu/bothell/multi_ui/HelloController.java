package edu.bothell.multi_ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
    @GetMapping("/hello")
    public String getMethodName( Model m ) {
        m.addAttribute("message", "DO IT NOW!!!!");
        return "hello";
    }
    
    
}
