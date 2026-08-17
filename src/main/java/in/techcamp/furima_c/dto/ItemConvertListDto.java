package in.techcamp.furima_c.dto;

import lombok.Data;

@Data
public class ItemConvertListDto {
    private Long id;
    private String name;
    private String image;
    private Integer price;
    private boolean soldout;
    private String shippingPayer;
}
