package pl.sokolak.teamtally.backend.user.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class UserRoleDto extends Data {
    private String name;
    private Boolean isDefault;
}
