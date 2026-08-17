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

// DTO群
import in.techcamp.furima_c.dto.ItemCreateDto;
import in.techcamp.furima_c.dto.ItemConvertDetailDto;
import in.techcamp.furima_c.dto.ItemConvertListDto;
import in.techcamp.furima_c.dto.ItemEditDto;

// Enum群
import in.techcamp.furima_c.enums.Category;
import in.techcamp.furima_c.enums.Condition;
import in.techcamp.furima_c.enums.DeliveryFeeType;
import in.techcamp.furima_c.enums.PrefectureType;
import in.techcamp.furima_c.enums.UntilDelivery;

import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 商品一覧表示
    @GetMapping("/")
    public String getAllItems(Model model){
        List<ItemConvertListDto> items = itemService.getAllItems();
        model.addAttribute("items", items);
        System.out.println("でーた : "+ items);
        return "items/index";
    }

    // 商品詳細表示
    @GetMapping("/items/{id}")
    public String showItem(
        @PathVariable("id")Long itemId,
        Model model,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        ItemConvertDetailDto itemDetail = itemService.showItemDetail(itemId);
        model.addAttribute("item", itemDetail);
        
        if(userDetails != null){
            model.addAttribute("currentUserId", userDetails.getUserEntity().getId());
        }
    
        return "items/show";
    }

    // 商品出品画面表示
    @GetMapping("/items/new")
    public String newItemForm(Model model) {
        model.addAttribute("itemCreateDto", new ItemCreateDto());
        model.addAttribute("categories", Category.values());
        model.addAttribute("conditions", Condition.values());
        model.addAttribute("deliveryfees", DeliveryFeeType.values());
        model.addAttribute("prefectures", PrefectureType.values());
        model.addAttribute("untildelivery", UntilDelivery.values());
        return "items/new";
    }

    // 商品出品処理
    @PostMapping("/items/new")
    public String addItem(@Validated @ModelAttribute("itemCreateDto")ItemCreateDto itemCreateDto,BindingResult bindingResult,
                          @AuthenticationPrincipal CustomUserDetails userDetails,
                          Model model
                        ) {
        
        
        if (itemCreateDto.getImage() == null || itemCreateDto.getImage().isEmpty()) {
            bindingResult.rejectValue("image", "error.image", "画像を選択してください");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("categories", Category.values());
            model.addAttribute("conditions", Condition.values());
            model.addAttribute("deliveryfees", DeliveryFeeType.values());
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("untildelivery", UntilDelivery.values());
            return "items/new";
        }
        try{
            Long currentUserId = userDetails.getUserEntity().getId();
            itemService.createItem(itemCreateDto,currentUserId);
            
        }catch(Exception e){
            log.error("出品処理中にエラーが発生しました", e);
            
            model.addAttribute("categories", Category.values());
            model.addAttribute("conditions", Condition.values());
            model.addAttribute("deliveryfees", DeliveryFeeType.values());
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("untildelivery", UntilDelivery.values());
            return "items/new";
        }
        
        return "redirect:/";
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

    // 編集画面の表示 (GET)
    @GetMapping("/items/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        ItemEditDto itemEditDto = itemService.getItemForEdit(itemId);

        if (userDetails == null || userDetails.getUserEntity() == null 
        || !itemEditDto.getUserId().equals(userDetails.getUserEntity().getId())) {
            return "redirect:/items/" + itemId;
        }
        model.addAttribute("itemEditDto", itemEditDto);
        model.addAttribute("categories", Category.values());
        model.addAttribute("conditions", Condition.values());
        model.addAttribute("deliveryfees", DeliveryFeeType.values());
        model.addAttribute("prefectures", PrefectureType.values());
        model.addAttribute("untildelivery", UntilDelivery.values());
        return "items/edit";
    }

    // 商品更新処理 (POST)
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(
            @PathVariable("itemId") Long itemId,
            @Validated @ModelAttribute("itemEditDto") ItemEditDto itemEditDto,
            BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        if (bindingResult.hasErrors()) {
            itemEditDto.setId(itemId);
            model.addAttribute("categories", Category.values());
            model.addAttribute("conditions", Condition.values());
            model.addAttribute("deliveryfees", DeliveryFeeType.values());
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("untildelivery", UntilDelivery.values());
            return "items/edit";
        }

        Long currentUserId = (userDetails != null && userDetails.getUserEntity() != null) 
            ? userDetails.getUserEntity().getId() : null;

        try {
            itemService.updateItem(itemId, itemEditDto, currentUserId);
        } catch (IllegalStateException e) {
            log.error("アクセス権限エラー: {}", e.getMessage());
            return "redirect:/items/" + itemId;
        } catch (Exception e) {
            log.error("商品の更新中にエラーが発生しました。商品ID: {}", itemId, e);
            return "redirect:/items/" + itemId + "/edit";
        }

        return "redirect:/items/" + itemId;
    }

}