package in.techcamp.furima_c.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserEntity {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String lastNameKana;
    private String firstNameKana;
    private LocalDate birthday;
}
