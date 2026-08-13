package in.techcamp.furima_c.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;



import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.ItemService;



@Controller
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 商品削除の処理
@PostMapping("items/{id}/delete")
 public String deleteItem(
    @PathVariable("id")Long itemId,
    @AuthenticationPrincipal CustomUserDetails userDetails
) {

    try{
        Long currentUserId = userDetails.getUserEntity().getId();
// userDetailsの中からuserEntityを取り出し、その中からIdを取り出す

        itemService.deleteItem(itemId, currentUserId);
    }catch (Exception e){
        System.out.println("エラー:" +e);
        return "redirect:/";
    }
    return "redirect:/";
}

    
    
}
