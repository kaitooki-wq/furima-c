package in.techcamp.furima_c.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class OrderFormTest {

    private Validator validator;
    private OrderForm orderForm;

    // 各テストの前に実行される初期設定
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        orderForm = new OrderForm();
        
        // 正常なデータをデフォルトとしてセットしておく
        orderForm.setToken("tok_abcdefghijk00000000000000000");
        orderForm.setPostalCode("123-4567");
        orderForm.setPrefecture(13); // 東京都などのIDを想定
        orderForm.setCity("渋谷区");
        orderForm.setBlock("道玄坂1-1-1");
        orderForm.setBuilding("渋谷ビル103");
        orderForm.setPhone("09012345678");
    }

    @Test
    @DisplayName("正常系：すべての値が正しく入力されていればエラーが発生しないこと")
    void testValidOrderForm() {
        Set<ConstraintViolation<OrderForm>> violations = validator.validate(orderForm);
        assertTrue(violations.isEmpty(), "エラーは発生しないはずです");
    }

    @Test
    @DisplayName("異常系：tokenが空だとエラーになること")
    void testBlankToken() {
        orderForm.setToken("");
        Set<ConstraintViolation<OrderForm>> violations = validator.validate(orderForm);
        
        assertEquals(1, violations.size());
        assertEquals("カード情報を正しく入力してください", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("異常系：郵便番号にハイフンがないとエラーになること")
    void testInvalidPostalCodeWithoutHyphen() {
        orderForm.setPostalCode("1234567");
        Set<ConstraintViolation<OrderForm>> violations = validator.validate(orderForm);
        
        assertEquals(1, violations.size());
        assertEquals("ハイフンを含めて入力してください", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("異常系：都道府県が未選択（null）だとエラーになること")
    void testNullPrefecture() {
        orderForm.setPrefecture(null);
        Set<ConstraintViolation<OrderForm>> violations = validator.validate(orderForm);
        
        assertEquals(1, violations.size());
        assertEquals("都道府県を選択してください", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("異常系：電話番号にハイフンが含まれているとエラーになること")
    void testInvalidPhoneWithHyphen() {
        orderForm.setPhone("090-1234-5678");
        Set<ConstraintViolation<OrderForm>> violations = validator.validate(orderForm);
        
        assertEquals(1, violations.size());
        assertEquals("整数でお書きください", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("異常系：電話番号が9桁以下だとエラーになること")
    void testTooShortPhone() {
        orderForm.setPhone("090123456");
        Set<ConstraintViolation<OrderForm>> violations = validator.validate(orderForm);
        
        assertEquals(1, violations.size());
        assertEquals("電話番号が短すぎます", violations.iterator().next().getMessage());
    }
}