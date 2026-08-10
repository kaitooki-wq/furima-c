package in.techcamp.furima_c.service;

import in.techcamp.furima_c.dto.ItemEditForm;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    // 編集画面用のモックデータ return (GET)
    public ItemEditForm getItemForEdit(Long itemId) {
        return ItemEditForm.builder()
                .id(itemId)
                .name("テスト商品名")
                .description("テスト商品説明")
                .categoryId(1L)
                .price(10000)
                .condition(1)
                .shippingPayer(1)
                .prefectureId(13)
                .shippingDays(1)
                .status(0)
                .build();
    }

    // 編集処理 (POST)
    public void updateItem(Long itemId, ItemEditForm form) {
        // ここで実際の更新処理を行う（DB更新など）
        System.out.println("Item 修正完了: ID=" + itemId + ", Name=" + form.getName());
    }
}