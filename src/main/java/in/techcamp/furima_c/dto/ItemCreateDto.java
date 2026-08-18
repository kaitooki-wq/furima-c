package in.techcamp.furima_c.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCreateDto {
    private Long userId;

    @NotBlank(message = "商品名は必須です")
    private String name;

    @NotBlank(message = "商品の説明は必須です")
    private String description;

    @NotNull(message = "カテゴリーを選択してください")
    @Min(value = 1, message = "カテゴリーを選択してください")
    private Long categoryId;

    @NotNull(message = "商品の状態を選択してください")
    @Min(value = 1, message = "商品の状態を選択してください")
    private Integer condition;

    @NotNull(message = "配送料の負担を選択してください")
    @Min(value = 1, message = "配送料の負担を選択してください")
    private Integer shippingPayer;

    @NotNull(message = "発送元の地域を選択してください")
    @Min(value = 1, message = "発送元の地域を選択してください")
    private Integer prefectureId;

    @NotNull(message = "発送までの日数を選択してください")
    @Min(value = 1, message = "発送までの日数を選択してください")
    private Integer shippingDays;

    @NotNull(message = "価格を入力してください")
    @Min(value = 300, message = "価格は300円以上で入力してください")
    @Max(value = 9999999, message = "価格は9,999,999円以下で入力してください")
    private Integer price;

    @NotNull(message = "画像を選択してください")
    private MultipartFile image;
}