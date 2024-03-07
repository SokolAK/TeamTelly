package pl.sokolak.teamtally.backend.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@Setter
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole userRole;

    public UserDto withoutPassword() {
        return UserDto.builder()
                .id(getId())
                .username(getUsername())
                .firstName(getFirstName())
                .lastName(getLastName())
                .email(getEmail())
                .userRole(getUserRole())
                .build();
    }

    public boolean isAdmin() {
        return Optional.ofNullable(userRole)
                .map(UserRole::getName)
                .filter(role -> role.equalsIgnoreCase("ADMIN"))
                .isPresent();
    }
}
