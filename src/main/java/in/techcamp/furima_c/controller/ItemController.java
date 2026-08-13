package in.techcamp.furima_c.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    
@GetMapping("/")
public String getAllItems(Model model){

    List<ItemListDto> items = itemService.getAllItems();

    model.addAttribute("items", items);

    return "items/index";

}

@PostMapping("items/{id}/delete")
 public String deleteItem(
    @PathVariable("id")Long itemId,
    @AuthenticationPrincipal CustomUserDetails userDetails
) {

    try{
        Long currentUserId = userDetails.getUserEntity().getId();


        itemService.deleteItem(itemId, currentUserId);
    }catch (Exception e){
        System.out.println("エラー:" +e);
        return "redirect:/";
    }
    return "redirect:/";

}
}
