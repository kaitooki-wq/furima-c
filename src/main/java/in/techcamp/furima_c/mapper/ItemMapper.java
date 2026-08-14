package in.techcamp.furima_c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import in.techcamp.furima_c.dto.ItemListDto;
import in.techcamp.furima_c.entity.ItemEntity;

@Mapper
public interface ItemMapper {
    @Select("SELECT id, name, image, price, shipping_payer FROM items ORDER BY id DESC")
    List<ItemListDto> findAll();

    // 商品削除
    @Delete("DELETE FROM items WHERE id = #{itemId}")
    int deleteByItemId(@Param("itemId") Long itemId);

    @Select("SELECT * FROM items WHERE id = #{itemId}")
    ItemEntity findById(@Param("itemId") Long itemId);

    // 商品出品
    @Insert("INSERT INTO items (seller_id, buyer_id, name, image, description, category_id, condition, shipping_payer, prefecture_id, shipping_days, price,status ) VALUES (#{sellerId},#{buyerId},#{name}, #{image},#{description},#{categoryId},#{condition},#{shippingPayer},#{prefectureId},#{shippingDays},#{price},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ItemEntity item);
}
