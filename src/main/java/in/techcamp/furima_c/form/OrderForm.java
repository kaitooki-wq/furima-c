package in.techcamp.furima_c.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderForm {

    @NotBlank(message = "カード情報を正しく入力してください")
    private String token;

    // 郵便番号
    @NotBlank(message = "郵便番号の項目は必須です")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はハイフンを含めて入力してください")
    private String postalCode;

    // 都道府県
    @NotNull(message = "都道府県を選択してください")
    private Integer prefecture;

    // 市区町村
    @NotBlank(message = "市区町村の項目は必須です")
    private String city;

    // 番地
    @NotBlank(message = "番地の項目は必須です")
    private String block;

    // 建物名
    private String building;

    // 電話番号
    @NotBlank(message = "電話番号の項目は必須です")
    @Pattern(regexp = "^[0-9]+$", message = "電話番号は整数でお書きください")
    @Size(min = 10, max = 11, message = "電話番号が短すぎます")
    private String phone;

}
