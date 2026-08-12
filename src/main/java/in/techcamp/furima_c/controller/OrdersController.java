package in.techcamp.furima_c.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.techcamp.furima_c.form.OrderForm;
import in.techcamp.furima_c.mapper.OrderMapper;
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

    @GetMapping("/order")
    public String index(Model model) {
        model.addAttribute("payjpPublicKey",payjpPublicKey);
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
        orderMapper.insert(orderForm.getPrice());
        return "redirect:/";
    }
}