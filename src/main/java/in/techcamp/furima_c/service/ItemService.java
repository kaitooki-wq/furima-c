package in.techcamp.furima_c.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.techcamp.furima_c.dto.ItemDetailDto;
import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.entity.ItemEntity;
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

     public void deleteItem(Long id, Long userId) throws Exception{
        ItemEntity existingItem = itemMapper.findById(id);
        if( existingItem == null){
            throw new IllegalArgumentException("指定された商品が見つかりません");
        }

        if (!existingItem.getSellerId().equals(userId)){
            throw new SecurityException("他のユーザーの商品は削除できません");
        }

        itemMapper.deleteByItemId(id);
    }

    public ItemDetailDto showItemDetail(Long id){

        ItemDetailDto itemDetail = itemMapper.findByitemId(id);
        return itemDetail;
    }
}
