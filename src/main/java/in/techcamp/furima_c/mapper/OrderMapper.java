package in.techcamp.furima_c.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.techcamp.furima_c.dto.BuyItemInfoDto;
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

    // 購入したかどうかを調べる
    @Select("SELECT EXISTS ( SELECT 1 FROM buy WHERE user_id = #{userId} AND item_id = #{itemId} )")
    boolean isCheckOrder(Long userId,Long itemId);
    
    // 購入したものがあればそのデータのidを返す
    @Select("SELECT id FROM buy WHERE user_id = #{userId} AND item_id = #{itemId}")
    Long searchBuyId(Long userId,Long itemId);

    // 商品に紐づいているuserIdの取得
    @Select("SELECT user_id FROM items WHERE id = #{id}")
    Long selectByUserId(Long id);

    // 購入する商品の取得
    @Select("SELECT id,img,name,price,shipping_payer FROM items WHERE id = #{id}")
    BuyItemInfoDto selectByItemId(Long id);

}   
