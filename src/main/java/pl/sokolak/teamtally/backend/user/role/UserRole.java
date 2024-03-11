package pl.sokolak.teamtally.backend.user.role;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.user.User;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_role", schema = "public")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    private UUID id;
    private String name;
}
