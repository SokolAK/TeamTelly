package pl.sokolak.teamtally.backend.user.role;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class UserRole extends AbstractEntity {
    private String name;
    private Boolean isDefault;
}
