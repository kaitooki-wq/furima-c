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

    @NotBlank(message = "Name does not be blank.")
    private String name;

    @NotBlank(message = "Description does not be blank.")
    private String description;

    @NotNull(message = "Please select a category.")
    private Long categoryId;

    @NotNull(message = "Price does not be blank.")
    @Min(value = 300, message = "Price must be at least 300.")
    private Integer price;

    private Integer condition;
    private Integer shippingPayer;
    private Integer prefectureId;
    private Integer shippingDays;
    private Integer status;
}