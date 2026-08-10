package in.techcamp.furima_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {
    @GetMapping("/")
    public String index() {
        return "items/index";
    }
}