package in.techcamp.furima_c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import in.techcamp.furima_c.dto.ItemDetailDto;
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


    @Select("""
            SELECT
            i.id, i.name, i.image, i.description,
            i.category_id, i.condition, i.shipping_payer, i.prefecture_id,
            i.shipping_days, i.price,
            u.nickname
            FROM items i
            JOIN users u ON i.user_id = u.id
            WHERE i.id = #{id}
            """)
    ItemDetailDto findByitemId(Long id);
}
