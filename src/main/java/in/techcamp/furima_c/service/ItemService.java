package in.techcamp.furima_c.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.techcamp.furima_c.dto.ItemCreateDto;
import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.entity.ItemEntity;
import in.techcamp.furima_c.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemMapper itemMapper;
// 商品一覧表示
    public List<ItemListDto> getAllItems(){

        List<ItemListDto> itemlist = itemMapper.findAll();
        return itemlist;
    }
// 商品削除
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
// 新規出品
     public void createItem(ItemCreateDto itemCreateDto, Long currentUserId)
     throws IOException {
        MultipartFile imageFile =itemCreateDto.getImage();
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
    }else{
        throw new IllegalArgumentException("画像ファイルが選択されていません");
    }

    // DB保存
    ItemEntity entity = new ItemEntity();

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
}
