package be.kdg.programming5.musicwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "view/login/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "view/login/registration";
    }
}
