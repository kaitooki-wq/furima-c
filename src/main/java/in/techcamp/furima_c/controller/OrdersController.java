package in.techcamp.furima_c.controller;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.techcamp.furima_c.enums.PrefectureType;
import in.techcamp.furima_c.form.OrderForm;
import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.BuyService;
import in.techcamp.furima_c.service.PayjpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final PayjpService payjpService;
    private final BuyService buyService;

    @Value("${payjp.public-key}")
    private String payjpPublicKey;

    // 商品購入ページ
    @GetMapping("/order/{id}")
    public String showOrder(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute OrderForm orderForm,
            Model model) {

        // ログインしているユーザーかどうか (ログインしてなかったらリダイレクトで返す)
        if (userDetails == null)
            return "redirect:/users/sign_in";

        // 出品した本人かどうか(出品した人だったらリダイレクトで返す)
        if (buyService.isSeller(userDetails.getUserEntity().getId(), id))
            return "redirect:/";

        // 売却済みかどうか
        if (buyService.isSoldOut(id))
            return "redirect:/";

        model.addAttribute("item", buyService.itemInfo(id));
        model.addAttribute("payjpPublicKey", payjpPublicKey);
        // model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("prefectures", PrefectureType.values());

        return "orders/index";
    }

    // 購入処理コントローラー
    @PostMapping("/order/{id}")
    public String itemOrder(
            @Valid @ModelAttribute OrderForm orderForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            Model model) {

        // バリデーションチェック
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", buyService.itemInfo(id));
            model.addAttribute("payjpPublicKey", payjpPublicKey);
            model.addAttribute("prefectures", PrefectureType.values());
            return "orders/index";
        }

        // ログインしているかどうか
        if (userDetails == null)
            return "redirect:/users/sign_in";

        Long userId = userDetails.getUserEntity().getId();
        Long itemId = id;

        // 出品した本人かどうか(出品した人だったらリダイレクトで返す)
        if (buyService.isSeller(userId, itemId))
            return "redirect:/";

        // 売却済みかどうか
        if (buyService.isSoldOut(itemId))
            return "redirect:/";

        try {
            // payservice
            payjpService.charge(buyService.itemInfo(itemId).getPrice(), orderForm.getToken());
            // 購入処理を保存
            buyService.insertUserInfo(orderForm, userId, itemId);

        } catch (RuntimeException e) {
            System.out.println("処理失敗 :" + e);
            throw new RuntimeCryptoException("処理に失敗しました");
        }

        return "redirect:/";
    }
}