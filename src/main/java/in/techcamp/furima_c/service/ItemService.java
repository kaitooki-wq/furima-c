package in.techcamp.furima_c.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.techcamp.furima_c.dto.ItemConvertDetailDto;
import in.techcamp.furima_c.dto.ItemConvertListDto;
import in.techcamp.furima_c.dto.ItemDetailDto;
import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.entity.ItemEntity;
import in.techcamp.furima_c.enums.Category;
import in.techcamp.furima_c.enums.Condition;
import in.techcamp.furima_c.enums.DeliveryFeeType;
import in.techcamp.furima_c.enums.PrefectureType;
import in.techcamp.furima_c.enums.UntilDelivery;
import in.techcamp.furima_c.mapper.ItemMapper;
import in.techcamp.furima_c.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;

    public List<ItemConvertListDto> getAllItems(){

        List<ItemListDto> itemlist = itemMapper.findAll();
        List<ItemConvertListDto> dtolist = itemlist.stream().map(item -> {
            ItemConvertListDto dto = new ItemConvertListDto();
            dto.setId(item.getId());
            dto.setImage(item.getImage());
            dto.setName(item.getName());
            dto.setPrice(item.getPrice());
            dto.setSoldout(orderMapper.isSoldOut(item.getId()));
            dto.setShippingPayer(DeliveryFeeType.fromCode(item.getShippingPayer()).getLabel());
            return dto;
            }).collect(Collectors.toList());

    return dtolist;

    }

    public void deleteItem(Long id, Long userId) throws Exception{
        ItemEntity existingItem = itemMapper.findById(id);
        if( existingItem == null){
            throw new IllegalArgumentException("指定された商品が見つかりません");
        }

        // item tableの中に入ってるuserIdと現在ログインしているuserIdを比べる
        if (!existingItem.getUserId().equals(userId)){
            throw new SecurityException("他のユーザーの商品は削除できません");
        }

        itemMapper.deleteByItemId(id);
    }

    // 商品詳細
    public ItemConvertDetailDto showItemDetail(Long id) {

        ItemDetailDto item = itemMapper.findByitemId(id);

        ItemConvertDetailDto dto = new ItemConvertDetailDto();

        dto.setId(item.getId());
        dto.setUserId(item.getUserId());
        dto.setName(item.getName());
        dto.setImage(item.getImage());
        dto.setPrice(item.getPrice());
        dto.setDescription(item.getDescription());
        dto.setNickname(item.getNickname());
        dto.setSoldout(orderMapper.isSoldOut(item.getId()));
        dto.setShippingPayer(DeliveryFeeType.fromCode(item.getShippingPayer()).getLabel());
        dto.setCategoryId(Category.fromCode(item.getCategoryId()).getDisplayName());
        dto.setCondition(Condition.fromCode(item.getCondition()).getDisplayName());
        dto.setPrefectureId(PrefectureType.fromCode(item.getPrefectureId()).getLabel());
        dto.setShippingDays(UntilDelivery.fromCode(item.getShippingDays()).getDisplayName());

        return dto;
    }
}
