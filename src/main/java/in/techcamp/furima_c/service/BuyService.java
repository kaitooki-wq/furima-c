package in.techcamp.furima_c.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.techcamp.furima_c.dto.BuyItemInfoDto;
import in.techcamp.furima_c.entity.AddressEntity;
import in.techcamp.furima_c.entity.BuyEntity;
import in.techcamp.furima_c.form.OrderForm;
import in.techcamp.furima_c.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final OrderMapper orderMapper;

    /**
     * 住所登録処理
     * 
     * @param orderForm
     * @param userId
     * @param productId
     */
    @Transactional
    public void insertUserInfo(OrderForm orderForm, Long userId, Long productId) {

        // 買ったかどうかを buy table に保存
        BuyEntity buy = new BuyEntity();
        buy.setUserId(userId);
        buy.setItemId(productId);

        orderMapper.buyInsert(buy);

        // 購入したもののidを返す
        long buyId = buy.getId();

        // orderFormから取得したものをaddressEntityに入れる
        AddressEntity address = new AddressEntity();

        address.setBuyId(buyId);
        address.setPostNumber(orderForm.getPostalCode());
        address.setPrefecture(orderForm.getPrefecture());
        address.setCity(orderForm.getCity());
        address.setBlock(orderForm.getBlock());
        address.setBuilding(orderForm.getBuilding());
        address.setPhone(orderForm.getPhone());

        orderMapper.buyUserInfoInsert(address);

    }

    // 購入する商品の情報
    public BuyItemInfoDto itemInfo(Long itmeId) {
        return orderMapper.selectByItemId(itmeId);
    }

    /**
     * 購入したかどうか
     * 
     * @param userId
     * @param productId
     * @return boolean
     */
    @Transactional
    public boolean isSoldOut(Long productId) {
        // 存在したらtrue
        return orderMapper.isSoldOut(productId);

    }

    /**
     * 購入した人が出品した人じゃないか
     * 
     * @param userId
     * @param productId
     * @return boolean
     */
    @Transactional
    public boolean isSeller(Long userId, Long productId) {
        // 購入した人だったらtrue
        return userId.equals(orderMapper.selectByUserId(productId));
    }

}
