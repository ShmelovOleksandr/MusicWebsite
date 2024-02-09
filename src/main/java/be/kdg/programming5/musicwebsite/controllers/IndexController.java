package be.kdg.programming5.musicwebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String getIndexPage(){
        return "view/index";
    }
}
