package in.techcamp.furima_c.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ItemConvertListDto {
    private Long id;
    private String name;
    private MultipartFile image;
    private Integer price;
    private boolean soldout;
    private String shippingPayer;
}
