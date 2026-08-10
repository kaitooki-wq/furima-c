package in.techcamp.furima_c.dto;

import in.techcamp.furima_c.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "ニックネームを入力してください")
    private String nickname;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスは不正な値です")
    private String email;

    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 6, message = "パスワードは6文字以上で入力してください")
    @ValidPassword
    private String password;

    @NotBlank(message = "パスワード(確認)を入力してください")
    private String passwordConfirm;

    @NotBlank(message = "名字を入力してください")
    @ValidPassword
    private String lastName;

    @NotBlank(message = "名前を入力してください")
    @ValidPassword
    private String firstName;

    @NotBlank(message = "名字(カナ)を入力してください")
    @ValidPassword
    private String lastNameKana;

    @NotBlank(message = "名前(カナ)を入力してください")
    @ValidPassword
    private String firstNameKana;

    @NotNull(message = "生年月日(年)を選択してください")
    private Integer birthYear;

    @NotNull(message = "生年月日(月)を選択してください")
    private Integer birthMonth;

    @NotNull(message = "生年月日(日)を選択してください")
    private Integer birthDay;
}