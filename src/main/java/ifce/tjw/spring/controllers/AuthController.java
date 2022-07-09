package ifce.tjw.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping(value = "/login")
    public String login() {
        return "login.html";
    }

    @GetMapping(value = "/login-error")
    public String login_error() {
        return "login-error.html";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "home.html";
    }
}
