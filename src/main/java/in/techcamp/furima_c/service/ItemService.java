package in.techcamp.furima_c.service;

import org.springframework.stereotype.Service;

import in.techcamp.furima_c.entity.ItemEntity;
import in.techcamp.furima_c.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper itemMapper;


    public void deleteItem(Long id, Long userId) throws Exception{
        ItemEntity existingItem = itemMapper.findById(id);
        if( existingItem == null){
            throw new IllegalArgumentException("指定された商品が見つかりません");
        }

        if (!existingItem.getSeller_id().equals(userId)){
            throw new SecurityException("他のユーザーの商品は削除できません");
        }

        itemMapper.deleteByItemId(id);
    }


    
}
