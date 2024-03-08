package pl.sokolak.teamtally.backend.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.role.UserRole;
import pl.sokolak.teamtally.backend.user.UserService;

import java.util.Optional;

@AllArgsConstructor
public class RepositoryUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }

    private UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        UserDto user = userService.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(mapUserRole(user))
                .build();
    }

    private String mapUserRole(UserDto user) {
        return Optional.ofNullable(user)
                .map(UserDto::getUserRole)
                .map(UserRole::getName)
                .map(String::toUpperCase)
                .map(r -> "ROLE_" + r)
                .orElse("none");
    }
}
