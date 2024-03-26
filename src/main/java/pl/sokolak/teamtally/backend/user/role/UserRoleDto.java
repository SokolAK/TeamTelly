package pl.sokolak.teamtally.backend.user.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sokolak.teamtally.abstracts.Data;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto extends Data {
    private String name;

    public UserRoleDto(UserRole role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
