package in.techcamp.furima_c.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemMapper itemMapper;

    public List<ItemListDto> getAllItems(){

        List<ItemListDto> itemlist = itemMapper.findAll();
        return itemlist;
    }
}
