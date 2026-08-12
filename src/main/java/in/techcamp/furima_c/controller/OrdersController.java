package in.techcamp.furima_c.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.techcamp.furima_c.form.OrderForm;
import in.techcamp.furima_c.mapper.OrderMapper;
import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.PayjpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final OrderMapper orderMapper;
    private final PayjpService payjpService;

    @Value("${payjp.public-key}")
    private String payjpPublicKey;

    @GetMapping("/order/{id}")
    public String index(@PathVariable Long id,
         @AuthenticationPrincipal CustomUserDetails userDetails,
          Model model) {

        // ログインしているユーザーかどうか (ログインしてなかったらリダイレクトで返す)
        if(userDetails == null) return "redirect:/users/sign_in";

        // 出品した本人かどうか(出品した人だったらリダイレクトで返す)
        

        // 売却済みかどうか

        model.addAttribute("payjpPublicKey", payjpPublicKey);
        model.addAttribute("orderForm", new OrderForm());
        return "orders/index";
    }

    @PostMapping("/orders")
    public String create(@Valid @ModelAttribute OrderForm orderForm,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "orders/index";
        }
        payjpService.charge(orderForm.getPrice(), orderForm.getToken());

        return "redirect:/";
    }
}