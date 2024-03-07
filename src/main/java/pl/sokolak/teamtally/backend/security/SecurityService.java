package pl.sokolak.teamtally.backend.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SecurityService {

    private final AuthenticationContext authenticationContext;
    private final UserService userService;

    public UserDto getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(UserDetails::getUsername)
                .map(userService::findFirstByEmail)
                .flatMap(Function.identity())
                .orElse(null);
    }

    public void logout() {
        authenticationContext.logout();
    }
}