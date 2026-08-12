package in.techcamp.furima_c.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import in.techcamp.furima_c.dto.ItemListDto;
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
}
