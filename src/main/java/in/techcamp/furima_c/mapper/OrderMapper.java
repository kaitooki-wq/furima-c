package in.techcamp.furima_c.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import in.techcamp.furima_c.entity.AddressEntity;
import in.techcamp.furima_c.entity.BuyEntity;

@Mapper
public interface OrderMapper {

    // 購入テーブルに保存
    @Insert("INSERT INTO buy (user_id,item_id) VALUES (#{userId},#{itemId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void buyInsert(BuyEntity buy);

    // 購入した人の住所を保存
    @Insert("""
            INSERT INTO address (
            buy_id,post_number,prefecture,city,block,building,phone
            ) VALUES (
            #{buyId},#{postNumber},#{prefecture},#{city},#{block},#{building},#{phone} )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void buyUserInfoInsert(AddressEntity address);

}
