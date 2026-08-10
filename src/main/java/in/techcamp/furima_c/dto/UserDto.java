package in.techcamp.furima_c.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$", message = "パスワードは半角英数字混合で入力してください")
    private String password;

    @NotBlank(message = "パスワード(確認)を入力してください")
    private String passwordConfirm;

    @NotBlank(message = "名字を入力してください")
    @Pattern(regexp = "^[ぁ-んァ-ヶ一-龥々ー]+$", message = "全角（漢字・ひらがな・カタカナ）で入力してください")
    private String lastName;

    @NotBlank(message = "名前を入力してください")
    @Pattern(regexp = "^[ぁ-んァ-ヶ一-龥々ー]+$", message = "全角（漢字・ひらがな・カタカナ）で入力してください")
    private String firstName;

    @NotBlank(message = "名字(カナ)を入力してください")
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "全角カタカナで入力してください")
    private String lastNameKana;

    @NotBlank(message = "名前(カナ)を入力してください")
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "全角カタカナで入力してください")
    private String firstNameKana;

    @NotNull(message = "生年月日(年)を選択してください")
    private Integer birthYear;

    @NotNull(message = "生年月日(月)を選択してください")
    private Integer birthMonth;

    @NotNull(message = "生年月日(日)を選択してください")
    private Integer birthDay;
}