package in.techcamp.furima_c.entity;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ItemEntity {
    private Long id;
    private Long userId;
    private String name;
    private MultipartFile image;
    private String description;
    private Long categoryId;
    private Integer condition;
    private Integer shippingPayer;
    private Integer prefectureId;
    private Integer shippingDays;
    private Integer price;
}
