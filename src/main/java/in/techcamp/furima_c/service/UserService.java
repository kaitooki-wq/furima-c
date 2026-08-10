package in.techcamp.furima_c.service;

import in.techcamp.furima_c.dto.UserDto;
import in.techcamp.furima_c.entity.UserEntity;
import in.techcamp.furima_c.mapper.UserMapper;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated // クラスレベルに付与することで引数の @Valid バリデーションを有効化します
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Transactional
    public void registerUser(@Valid UserDto userDto) {
        // パスワード一致チェック
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("パスワードとパスワード(確認)が一致しません");
        }

        // メールアドレス重複チェック
        if (existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("指定されたメールアドレスは既に登録されています");
        }

        UserEntity user = new UserEntity();
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setLastNameKana(userDto.getLastNameKana());
        user.setFirstNameKana(userDto.getFirstNameKana());

        LocalDate birthday = LocalDate.of(
            userDto.getBirthYear(),
            userDto.getBirthMonth(),
            userDto.getBirthDay()
        );
        user.setBirthday(birthday);

        userMapper.insert(user);
    }
}