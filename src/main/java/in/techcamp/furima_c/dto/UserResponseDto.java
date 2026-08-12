package in.techcamp.furima_c.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ユーザー情報をクライアントへ返却（レスポンス）するためのDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Long id;
  private String nickname;
  private String email;
  private String lastName;
  private String firstName;
  private String lastNameKana;
  private String firstNameKana;
  private String birthDate;
}