package pl.sokolak.teamtally.backend.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "user_role", schema = "public")
public class UserRole {
    @Id
    private UUID id;
    @Getter
    private String name;
}
