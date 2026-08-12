package in.techcamp.furima_c.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  // 半角英字(a-z, A-Z)と半角数字(0-9)の両方を少なくとも1文字以上含む正規表現
  private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$";

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    // @NotBlank で必須チェックを行っているため、nullまたは空文字の場合はここでは通す (重複エラーを防ぐため)
    if (password == null || password.isEmpty()) {
      return true;
    }

    // 正規表現にマッチするか検証
    return password.matches(PASSWORD_PATTERN);
  }
}