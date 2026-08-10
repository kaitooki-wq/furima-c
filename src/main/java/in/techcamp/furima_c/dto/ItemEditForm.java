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

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotNull(message = "Please select a category.")
    private Long categoryId;

    @NotNull(message = "Price cannot be blank.")
    @Min(value = 300, message = "Price must be at least 300.")
    private Integer price;

    @NotNull(message = "Please select a condition.")
    private Integer condition;
    
    @NotNull(message = "Please select a shipping payer.")
    private Integer shippingPayer;

    @NotNull(message = "Please select a prefecture.")
    private Integer prefectureId;

    @NotNull(message = "Please select shipping days.")
    private Integer shippingDays;

    @NotNull(message = "Please select a status.")
    private Integer status;
}