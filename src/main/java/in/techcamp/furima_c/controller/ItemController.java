package in.techcamp.furima_c.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import in.techcamp.furima_c.dto.ItemCreateDto;
import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemCreateDto itemCreateDto;
// 商品一覧表示
@GetMapping("/")
public String getAllItems(Model model){

    List<ItemListDto> items = itemService.getAllItems();

    model.addAttribute("items", items);

    return "items/index";

}
// 商品削除
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
// 商品出品
@PostMapping("/items/new")
public String addItem(@Validated @ModelAttribute("itemCreateDto")ItemCreateDto itemCreateDto,BindingResult bindingResult,
                      @AuthenticationPrincipal CustomUserDetails userDetails
                    ) {
    
    if(bindingResult.hasErrors()){
        return "items/new";
    }
    try{

        Long currentUserId = userDetails.getUserEntity().getId();

        itemService.createItem(itemCreateDto,currentUserId);
        
    }catch(Exception e){
        return "items/new";
    }
    
    return "redirect:/";
}

}
