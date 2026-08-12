package in.techcamp.furima_c.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEditForm {

    private Long id;

    @NotBlank(message = "商品名を入力してください")
    private String name;

    @NotBlank(message = "商品の説明を入力してください")
    private String description;

    @NotNull(message = "カテゴリーを選択してください")
    private Long categoryId;

    @NotNull(message = "価格を入力してください")
    @Min(value = 300, message = "価格は300円以上で入力してください")
    private Integer price;

    @NotNull(message = "商品の状態を選択してください")
    private Integer condition;
    
    @NotNull(message = "配送の負担を選択してください")
    private Integer shippingPayer;

    @NotNull(message = "配送元の地域を選択してください")
    private Integer prefectureId;

    @NotNull(message = "配送までの日数を選択してください")
    private Integer shippingDays;

    @NotNull(message = "ステータスは必須です。")
    @Builder.Default
    private Integer status=0;
}