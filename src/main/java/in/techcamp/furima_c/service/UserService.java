package in.techcamp.furima_c.service;

import in.techcamp.furima_c.entity.UserEntity;
import in.techcamp.furima_c.form.UserRegistrationForm;
import in.techcamp.furima_c.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
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
    public void registerUser(UserRegistrationForm form) {
        UserEntity user = new UserEntity();
        user.setNickname(form.getNickname());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setLastNameKana(form.getLastNameKana());
        user.setFirstNameKana(form.getFirstNameKana());
        
        LocalDate birthday = LocalDate.of(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
        user.setBirthday(birthday);

        userMapper.insert(user);
    }
}