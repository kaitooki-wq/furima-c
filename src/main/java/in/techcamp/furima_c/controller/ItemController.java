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

// DTO群（両方のブランチで使われているものを統合）
import in.techcamp.furima_c.dto.ItemCreateDto;
import in.techcamp.furima_c.dto.ItemConvertDetailDto;
import in.techcamp.furima_c.dto.ItemConvertListDto;
import in.techcamp.furima_c.dto.ItemEditDto;

// Enum群（出品機能用）
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

    // 商品一覧表示 (mainブランチの変更を反映)
    @GetMapping("/")
    public String getAllItems(Model model){
        List<ItemConvertListDto> items = itemService.getAllItems();
        model.addAttribute("items", items);
        System.out.println("でーた : "+ items);
        return "items/index";
    }

    // 商品詳細表示 (mainブランチで追加された機能)
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

    // 商品出品画面表示 (現在のブランチの機能)
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

    // 商品出品処理 (現在のブランチの機能)
    @PostMapping("/items/new")
    public String addItem(@Validated @ModelAttribute("itemCreateDto")ItemCreateDto itemCreateDto,BindingResult bindingResult,
                          @AuthenticationPrincipal CustomUserDetails userDetails,
                          Model model
                        ) {
        
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
            log.error("出品処理中にエラーが発生しました", e); // エラー原因をターミナルに出すための一文
            
            model.addAttribute("categories", Category.values());
            model.addAttribute("conditions", Condition.values());
            model.addAttribute("deliveryfees", DeliveryFeeType.values());
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("untildelivery", UntilDelivery.values());
            return "items/new";
        }
        
        return "redirect:/";
    }

    // 商品削除 (両方のブランチで共通)
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
        // ここで、itemIdに基づいて商品情報を取得し、ItemEditDtoに変換する処理を行う
        ItemEditDto itemEditDto = itemService.getItemForEdit(itemId);

        // 権限チェック: 現在のユーザーが出品者でない場合、詳細画面にリダイレクト
        if (!itemEditDto.getUserId().equals(userDetails.getUserEntity().getId())) {
        log.warn("権限のないユーザーによる編集アクセス: itemId={}, userId={}", itemId, userDetails.getUserEntity().getId());
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

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(
            @PathVariable("itemId") Long itemId,
            @Validated @ModelAttribute("itemEditDto") ItemEditDto itemEditDto,
            BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) { // バリデーションエラーがある場合は編集画面に戻す

        if (bindingResult.hasErrors()) {
            itemEditDto.setId(itemId); // 商品IDをDTOにセット
            model.addAttribute("categories", Category.values());
            model.addAttribute("conditions", Condition.values());
            model.addAttribute("deliveryfees", DeliveryFeeType.values());
            model.addAttribute("prefectures", PrefectureType.values());
            model.addAttribute("untildelivery", UntilDelivery.values());
            return "items/edit";
        }
        // 更新処理を呼び出す
        try {
            itemService.updateItem(itemId, itemEditDto, userDetails.getUserEntity().getId());
        } catch(IllegalStateException e) {
            log.error("アクセス権限エラー: {}", e.getMessage());
            return "redirect:/items/" + itemId;
        } catch (Exception e) {
            log.error("商品の更新中にエラーが発生しました。商品ID: {}", itemId, e);
            return "redirect:/items/" + itemId + "/edit";
        }

        return "redirect:/items/" + itemId;
    }

}