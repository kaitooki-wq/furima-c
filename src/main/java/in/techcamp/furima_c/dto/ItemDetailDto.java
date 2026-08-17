package in.techcamp.furima_c.dto;


import lombok.Data;

@Data
public class ItemDetailDto {
    private Long id;
    private String name;
    private Long userId;
    private String image;
    private String description;
    private Integer categoryId;
    private Integer condition;
    private Integer shippingPayer;
    private Integer prefectureId;
    private Integer shippingDays;
    private Integer price;

    private String nickname;
}
