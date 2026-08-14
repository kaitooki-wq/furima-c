package in.techcamp.furima_c.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.techcamp.furima_c.dto.ItemConvertDetailDto;
import in.techcamp.furima_c.dto.ItemConvertListDto;
import in.techcamp.furima_c.dto.ItemCreateDto;
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

    // 商品一覧表示 (DTO変換を利用した拡張版を採用)
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

    // 商品削除
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

    // 新規出品
    public void createItem(ItemCreateDto itemCreateDto, Long currentUserId) throws IOException {
        MultipartFile imageFile = itemCreateDto.getImage();
        String savedFileName = null;

        if (imageFile != null && !imageFile.isEmpty()){
            String originalName = imageFile.getOriginalFilename();

            if(originalName != null && originalName.contains(".")){
                String extension = originalName.substring(originalName.lastIndexOf("."));
                savedFileName = UUID.randomUUID().toString() + extension;

                Path uploadPath = Paths.get("uploads/").toAbsolutePath().normalize();
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(savedFileName);
                imageFile.transferTo(filePath);
            }
        } else {
            throw new IllegalArgumentException("画像ファイルが選択されていません");
        }

        // DB保存
        ItemEntity entity = new ItemEntity();

        entity.setUserId(currentUserId); // 出品者のIDをセット
        entity.setName(itemCreateDto.getName());
        entity.setImage(savedFileName);
        entity.setDescription(itemCreateDto.getDescription());
        entity.setCategoryId(itemCreateDto.getCategoryId());
        entity.setCondition(itemCreateDto.getCondition());
        entity.setShippingPayer(itemCreateDto.getShippingPayer());
        entity.setPrefectureId(itemCreateDto.getPrefectureId());
        entity.setShippingDays(itemCreateDto.getShippingDays());
        entity.setPrice(itemCreateDto.getPrice());

        itemMapper.insert(entity);
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