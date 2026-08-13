package in.techcamp.furima_c.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import in.techcamp.furima_c.entity.ItemEntity;

@Mapper
public interface ItemMapper {
// 商品削除
    @Delete("DELETE FROM items WHERE id = #{itemId}")
    int deleteByItemId(@Param("itemId") Long itemid);

    @Select("SELECT * FROM items WHERE id = #{itemId}")
    ItemEntity findById(@Param("itemId") Long itemId);

    
  
    
}
