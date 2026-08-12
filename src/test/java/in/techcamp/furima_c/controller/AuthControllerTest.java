package in.techcamp.furima_c.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import in.techcamp.furima_c.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  @DisplayName("GET /users/sign_in - ログイン画面が正常に表示される")
  void showSignInForm_Success() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users/sign_in"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/sign_in"));
  }

  @Test
  @DisplayName("GET /users/sign_up - 新規登録画面が正常に表示される")
  void showSignUpForm_Success() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users/sign_up"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/sign_up"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("userDto"));
  }

  @Test
  @DisplayName("POST /users/sign_up - 入力項目が未入力の場合、バリデーションエラーになり登録画面に戻る")
  void signUp_ValidationError_ReturnsSignUpView() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/users/sign_up")
            .param("nickname", "")
            .param("email", "invalid-email")
            .param("password", "123"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("users/sign_up"))
        .andExpect(MockMvcResultMatchers.model().hasErrors());
  }

  @Test
  @DisplayName("POST /users/sign_up - 正常なデータ送信時、ログイン画面へリダイレクトされる")
  void signUp_Success_RedirectsToSignIn() throws Exception {
    doNothing().when(userService).registerUser(any());

    mockMvc.perform(MockMvcRequestBuilders.post("/users/sign_up")
            .param("nickname", "テスト太郎")
            .param("email", "test@example.com")
            .param("password", "password123")
            .param("passwordConfirm", "password123")
            .param("lastName", "山田")
            .param("firstName", "太郎")
            .param("lastNameKana", "ヤマダ")
            .param("firstNameKana", "タロウ")
            .param("birthYear", "2000")
            .param("birthMonth", "1")
            .param("birthDay", "1"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/users/sign_in?registered=true"));
  }
}