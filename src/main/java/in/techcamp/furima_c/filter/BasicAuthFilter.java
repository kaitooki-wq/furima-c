package in.techcamp.furima_c.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class BasicAuthFilter extends OncePerRequestFilter {

//Basic認証を管理する変数。オン＝true,オフ=false。 
  @Value("${basic.auth.enabled:false}")
  private boolean enabled;

  @Value("${basic.auth.username}")
  private String username;

  @Value("${basic.auth.password}")
  private String password;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (!enabled) {
      filterChain.doFilter(request, response);
      return;
    }

    String header = request.getHeader("Authorization");

    if (header != null && header.toLowerCase().startsWith("basic ")) {
      String base64Credentials = header.substring("Basic ".length()).trim();
      try {
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);

        if (values.length == 2 && username.equals(values[0]) && password.equals(values[1])) {
          filterChain.doFilter(request, response);
          return;
        }
      } catch (IllegalArgumentException e) {

      }
    }
    response.setHeader("WWW-Authenticate", "Basic realm=\"Restricted Access\"");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("Basic Authentication Required");
   
  }
}