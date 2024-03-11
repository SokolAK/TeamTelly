package pl.sokolak.teamtally.backend.user.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserRoleDto {
    private UUID id;
    private String name;

    public UserRoleDto(UserRole role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
