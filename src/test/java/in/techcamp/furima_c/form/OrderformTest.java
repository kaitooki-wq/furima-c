package in.techcamp.furima_c.form;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class OrderFormTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("内容に問題ない場合")
    class WhenValid {

        @Test
        @DisplayName("priceがあれば保存ができること")
        void priceがあれば保存ができること() {
            OrderForm form = new OrderForm();
            form.setPrice(3000);

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertTrue(violations.isEmpty());
        }
    }

    @Nested
    @DisplayName("内容に問題がある場合")
    class WhenInvalid {

        @Test
        @DisplayName("priceが空では保存ができないこと")
        void priceが空では保存ができないこと() {
            OrderForm form = new OrderForm();
            form.setPrice(null);

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("金額を入力してください")));
        }
    }
}
