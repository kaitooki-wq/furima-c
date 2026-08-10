package in.techcamp.furima_c.security;

import in.techcamp.furima_c.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// SpringSecurityに情報を渡す
@Getter
public class CustomUserDetails implements UserDetails {

  private final UserEntity userEntity;

  public CustomUserDetails(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() { return true; }

  @Override
  public boolean isAccountNonLocked() { return true; }

  @Override
  public boolean isCredentialsNonExpired() { return true; }

  @Override
  public boolean isEnabled() { return true; }
}