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
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "ハイフンを含めて入力してください")
    private String postalCode;

    // 都道府県
    @NotNull(message = "都道府県を選択してください")
    private Integer prefecture;

    // 市区町村
    @NotBlank
    private String city;

    // 番地
    @NotBlank
    private String block;

    // 建物名
    private String building;

    // 電話番号
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "Phone number is invalid. Input only number")
    @Size(min = 10, max = 11, message = "Phone number is too short")
    private String phone;

}