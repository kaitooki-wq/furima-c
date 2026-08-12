package in.techcamp.furima_c.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders (price, created_at, updated_at) VALUES (#{price}, NOW(), NOW())")
    void insert(int price);
}
