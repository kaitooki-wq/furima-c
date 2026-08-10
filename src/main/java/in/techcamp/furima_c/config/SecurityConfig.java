package in.techcamp.furima_c.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserDetailsService userDetailsService;

  public SecurityConfig(@Lazy UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .userDetailsService(userDetailsService)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            // 静的リソース(css, js, images等)を許可
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()

            // 認証不要でアクセス許可するパス
            .requestMatchers(
                "/",
                "/users/sign_in/**",
                "/users/sign_up/**",  
                "/items",
                "/items/*",
                "/items/search"
            ).permitAll()

            // 上記以外はログイン必須
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginProcessingUrl("/users/sign_in")
            .loginPage("/users/sign_in")
            .defaultSuccessUrl("/")
            .failureUrl("/users/sign_in?error=true")
            .usernameParameter("email")
            .passwordParameter("password")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}