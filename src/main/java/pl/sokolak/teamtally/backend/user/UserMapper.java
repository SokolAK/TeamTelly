package pl.sokolak.teamtally.backend.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sokolak.teamtally.backend.user.role.UserRole;
import pl.sokolak.teamtally.backend.user.role.UserRoleDto;

import java.util.Optional;
import java.util.UUID;

public class UserMapper {
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .userRole(new UserRoleDto(entity.getUserRole()))
                .build();
    }

    public User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(encodePassword(dto.getPassword()))
                .userRole(new UserRole(dto.getUserRole().getId(), dto.getUserRole().getName()))
                .build();
    }

    public User toEntityWithPassword(UserDto dto, String password) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userRole(new UserRole(dto.getUserRole().getId(), dto.getUserRole().getName()))
                .password(password)
                .build();
    }

    private String encodePassword(String password) {
        return Optional.ofNullable(password)
                .map(p -> new BCryptPasswordEncoder().encode(p))
                .orElse(null);
    }
}
