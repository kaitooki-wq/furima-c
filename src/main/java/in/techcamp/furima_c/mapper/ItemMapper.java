package in.techcamp.furima_c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.techcamp.furima_c.dto.ItemListDto;

@Mapper
public interface ItemMapper {
    @Select("SELECT id, name, price, shipping_payer FROM items ORDER BY id DESC")
    List<ItemListDto> findAll();
}
