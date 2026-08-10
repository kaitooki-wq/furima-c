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

  //ログイン画面
  @GetMapping("/sign_in")
  public String showSignInForm() {
    return "users/sign_in";
  }

  // 新規登録画面
  @GetMapping("/sign_up")
  public String showSignUpForm(@ModelAttribute("userDto") UserDto userDto) {
    return "users/sign_up";
  }

  //アカウント作成
  @PostMapping("/sign_up")
  public String signUp(
      @Valid @ModelAttribute("userDto") UserDto userDto,
      BindingResult bindingResult,
      Model model
  ) {
    if (bindingResult.hasErrors()) {
      return "users/sign_up";
    }

    try {
      userService.registerUser(userDto);
      return "redirect:/users/sign_in?registered=true";
    } catch (IllegalArgumentException e) {
      bindingResult.reject("registrationError", e.getMessage());
      return "users/sign_up";
    }
  }
}