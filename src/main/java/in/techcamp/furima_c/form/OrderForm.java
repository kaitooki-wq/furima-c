package in.techcamp.furima_c.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderForm {

    @NotNull(message = "金額を入力してください")
    private Integer price;

    @NotBlank(message = "カード情報を正しく入力してください")
    private String token;
}