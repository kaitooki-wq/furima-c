package in.techcamp.furima_c.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 生年月日が正常かどうかを判定する
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.TYPE }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {

  String message() default "不正な日付です";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}