package in.techcamp.furima_c.validation;

import in.techcamp.furima_c.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDate;

// 生年月日を受け取り、正常であるかどうかを判定するクラスに渡す
public class DateValidator implements ConstraintValidator<ValidDate, UserDto> {

  @Override
  public boolean isValid(UserDto dto, ConstraintValidatorContext context) {
    if (dto == null) {
      return true;
    }

    Integer year = dto.getBirthYear();
    Integer month = dto.getBirthMonth();
    Integer day = dto.getBirthDay();

    if (year == null || month == null || day == null) {
      return true;
    }

    try {
      LocalDate.of(year, month, day);
      return true;
    } catch (DateTimeException e) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("birthDay") 
          .addConstraintViolation();
      return false;
    }
  }
}