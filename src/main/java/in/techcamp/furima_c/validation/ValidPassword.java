package in.techcamp.furima_c.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordValidator.class) // 検証を行うクラスを指定
@Target({ ElementType.FIELD, ElementType.PARAMETER }) // フィールドとパラメータに付与可能
@Retention(RetentionPolicy.RUNTIME) // 実行時まで保持
public @interface ValidPassword {

  String message() default "パスワードは半角英数字混合で入力してください";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}