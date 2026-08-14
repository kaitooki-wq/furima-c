package in.techcamp.furima_c.dto;


import lombok.Data;

@Data
public class ItemDetailDto {
    private Long id;
    private Long sellerId;
    private Long buyerId;
    private String name;
    private String image;
    private String description;
    private Long categoryId;
    private Integer condition;
    private Integer shippingPayer;
    private Integer prefectureId;
    private Integer shippingDays;
    private Integer price;
    private Integer status;

    private String nickname;
}
