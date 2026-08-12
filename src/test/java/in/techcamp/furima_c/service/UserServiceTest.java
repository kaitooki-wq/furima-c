package in.techcamp.furima_c.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import in.techcamp.furima_c.dto.UserDto;
import in.techcamp.furima_c.entity.UserEntity;
import in.techcamp.furima_c.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  private UserDto validUserDto;

  @BeforeEach
  void setUp() {
    validUserDto = new UserDto();
    validUserDto.setNickname("テスト太郎");
    validUserDto.setEmail("test@example.com");
    validUserDto.setPassword("password123");
    validUserDto.setPasswordConfirm("password123");
    validUserDto.setLastName("山田");
    validUserDto.setFirstName("太郎");
    validUserDto.setLastNameKana("ヤマダ");
    validUserDto.setFirstNameKana("タロウ");
    validUserDto.setBirthYear(2000);
    validUserDto.setBirthMonth(1);
    validUserDto.setBirthDay(1);
  }

  @Nested
  @DisplayName("ユーザー登録処理 (registerUser)")
  class RegisterUserTest {

    @Test
    @DisplayName("正常系: 正しいデータが渡された場合、ユーザーが暗号化されて登録される")
    void registerUser_Success() {
      // Arrange
      when(userMapper.existsByEmail(validUserDto.getEmail())).thenReturn(false);
      when(passwordEncoder.encode(validUserDto.getPassword())).thenReturn("encodedPassword");

      // Act
      userService.registerUser(validUserDto);

      // Assert
      verify(userMapper).existsByEmail(validUserDto.getEmail());
      verify(passwordEncoder).encode("password123");
      verify(userMapper).insert(any(UserEntity.class));
    }

    @Test
    @DisplayName("異常系: パスワードと確認用パスワードが不一致の場合、例外がスローされる")
    void registerUser_PasswordMismatch_ThrowsException() {
      // Arrange
      validUserDto.setPasswordConfirm("differentPassword");

      // Act & Assert
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> userService.registerUser(validUserDto)
      );

      assertEquals("パスワードとパスワード(確認)が一致しません", exception.getMessage());
      verify(userMapper, never()).insert(any());
    }

    @Test
    @DisplayName("異常系: 既に登録済みのメールアドレスの場合、例外がスローされる")
    void registerUser_EmailAlreadyExists_ThrowsException() {
      // Arrange
      when(userMapper.existsByEmail(validUserDto.getEmail())).thenReturn(true);

      // Act & Assert
      IllegalArgumentException exception = assertThrows(
          IllegalArgumentException.class,
          () -> userService.registerUser(validUserDto)
      );

      assertEquals("指定されたメールアドレスは既に登録されています", exception.getMessage());
      verify(userMapper, never()).insert(any());
    }
  }
}