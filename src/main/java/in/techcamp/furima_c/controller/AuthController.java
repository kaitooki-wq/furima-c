package in.techcamp.furima_c.controller;

import in.techcamp.furima_c.form.UserRegistrationForm;
import in.techcamp.furima_c.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign_in")
    public String showSignInForm() {
        return "users/sign_in";
    }

    @GetMapping("/sign_up")
    public String showSignUpForm(Model model) {
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        return "users/sign_up";
    }

    @PostMapping("/sign_up")
    public String registerUser(@Validated @ModelAttribute UserRegistrationForm userRegistrationForm, BindingResult bindingResult) {
        
        // パスワードの一致チェック
        if (!userRegistrationForm.getPassword().isEmpty() && !userRegistrationForm.getPassword().equals(userRegistrationForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードとパスワード（確認用）が一致しません");
        }

        // メールアドレスの重複チェック
        if (!userRegistrationForm.getEmail().isEmpty() && userService.existsByEmail(userRegistrationForm.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "このメールアドレスは既に登録されています");
        }

        if (bindingResult.hasErrors()) {
            return "users/sign_up";
        }

        userService.registerUser(userRegistrationForm);
        return "redirect:/";
    }
}