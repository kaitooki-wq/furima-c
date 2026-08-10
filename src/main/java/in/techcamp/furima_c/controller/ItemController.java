package in.techcamp.furima_c.controller;

import in.techcamp.furima_c.dto.ItemEditForm;
import in.techcamp.furima_c.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 編集画面の表示 (GET)
    @GetMapping("/items/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        ItemEditForm form = itemService.getItemForEdit(itemId);
        model.addAttribute("itemForm", form);
        return "items/edit";
    }

    // 2. 編集処理の実行 (POST)
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(
            @PathVariable("itemId") Long itemId,
            @Valid @ModelAttribute("itemForm") ItemEditForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/edit";
        }

        itemService.updateItem(itemId, form);
        return "redirect:/items/" + itemId;
    }
}