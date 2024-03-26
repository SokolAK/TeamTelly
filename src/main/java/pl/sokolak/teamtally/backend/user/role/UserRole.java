package pl.sokolak.teamtally.backend.user.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntity;

@Entity
@Table(name = "user_role", schema = "public")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends AbstractEntity {
    private String name;
}
