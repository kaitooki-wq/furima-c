package in.techcamp.furima_c.entity;



import lombok.Data;

@Data
public class ItemEntity {
    private Long id;
    private Long userId;
    private String name;
    private String image;
    private String description;
    private Long categoryId;
    private Integer condition;
    private Integer shippingPayer;
    private Integer prefectureId;
    private Integer shippingDays;
    private Integer price;
    private Integer userId; 
}
