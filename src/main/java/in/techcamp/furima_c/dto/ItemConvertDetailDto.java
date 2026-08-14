package in.techcamp.furima_c.dto;


import lombok.Data;

@Data
public class ItemConvertDetailDto {
    private Long id;
    private String name;
    private String image;
    private Long userId;
    private String description;
    private String categoryId;
    private String condition;
    private String shippingPayer;
    private String prefectureId;
    private String shippingDays;
    private Integer price;
    private boolean soldout;

    private String nickname;
}