package in.techcamp.furima_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//後々別のコントローラーが作成されると思うので、そちらとこのファイルを合成させる方針です。
public class TopController {
    @GetMapping("/")
    public String index() {
        return "items/index";
    }
}