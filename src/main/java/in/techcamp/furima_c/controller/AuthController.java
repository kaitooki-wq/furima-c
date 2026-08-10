package in.techcamp.furima_c.controller;

import in.techcamp.furima_c.dto.UserDto;
import in.techcamp.furima_c.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  @GetMapping("/sign_in")
  public String showSignInForm() {
    return "users/sign_in";
  }

  @GetMapping("/sign_up")
  public String showSignUpForm(@ModelAttribute("userDto") UserDto userDto) {
    return "users/sign_up";
  }

  @PostMapping("/sign_up")
  public String signUp(
      @Valid @ModelAttribute("userDto") UserDto userDto,
      BindingResult bindingResult,
      Model model
  ) {
    // 1. Dtoのバリデーションエラーチェック
    if (bindingResult.hasErrors()) {
      return "users/sign_up";
    }

    try {
      userService.registerUser(userDto);
      return "redirect:/users/sign_in?registered=true";
    } catch (IllegalArgumentException e) {
      // 2. サービス層で発生した例外（メールアドレス重複・パスワード不一致等）をエラーとして追加
      bindingResult.reject("registrationError", e.getMessage());
      return "users/sign_up";
    }
  }
}