package in.techcamp.furima_c.dto;


import lombok.Data;

@Data
public class ItemListDto {
    private Long id;
    private String name;
    private String image;
    private Integer price;
    private Integer shippingPayer;
}
