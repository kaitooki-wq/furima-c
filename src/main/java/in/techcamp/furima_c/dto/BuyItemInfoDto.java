package in.techcamp.furima_c.dto;

import lombok.Data;

@Data
public class BuyItemInfoDto {

    // 商品名、画像、値段、配送料負担
    private Long id;
    private String img;
    private String name;
    private Integer price;
    private Short shippingPayer;

}
