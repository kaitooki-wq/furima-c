package in.techcamp.furima_c.entity;


import lombok.Data;

@Data
public class ItemEntity {
    private Long id;
    private Long seller_id;
    private Long buyer_id;
    private String name;
    private String description;
    private Long category_id;
    private Integer condition;
    private Integer shipping_payer;
    private Integer prefecture_id;
    private Integer shipping_days;
    private Integer price;
    private Integer status; 
}
