package in.techcamp.furima_c.security;

import in.techcamp.furima_c.entity.UserEntity;
import in.techcamp.furima_c.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) { this.userMapper = userMapper; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userMapper.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return new UserDetailsImpl(user);
    }
}