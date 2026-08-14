package in.techcamp.furima_c.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import in.techcamp.furima_c.dto.ItemConvertDetailDto;
import in.techcamp.furima_c.dto.ItemConvertListDto;
import in.techcamp.furima_c.dto.ItemDetailDto;
import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    
@GetMapping("/")
public String getAllItems(Model model){

    List<ItemConvertListDto> items = itemService.getAllItems();

    model.addAttribute("items", items);

    System.out.println("でーた : "+ items);

    return "items/index";

}

@GetMapping("/items/{id}")
public String showItem(
    @PathVariable("id")Long itemId,
    Model model,
    @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service層で処理
        ItemConvertDetailDto itemDetail = itemService.showItemDetail(itemId);

       model.addAttribute("item", itemDetail);
        
        if(userDetails != null){
            model.addAttribute("currentUserId", userDetails.getUserEntity().getId());
        }
    
    return "items/show";
}

@PostMapping("/items/{id}/delete")
 public String deleteItem(
    @PathVariable("id")Long itemId,
    @AuthenticationPrincipal CustomUserDetails userDetails
) {

    try{
        Long currentUserId = userDetails.getUserEntity().getId();


        itemService.deleteItem(itemId, currentUserId);
    }catch (Exception e){

        log.error("商品の削除中にエラーが発生しました。商品ID: {}", itemId, e);
        return "redirect:/";
    }
    return "redirect:/";

}
}
