package pl.sokolak.teamtally.backend.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntity;
import pl.sokolak.teamtally.backend.participant.Participant;
import pl.sokolak.teamtally.backend.user.role.UserRole;

import java.util.Set;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "public")
public class User extends AbstractEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String email;
    private String password;
    private byte[] photo;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private UserRole userRole;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Participant> participants;
    private Boolean logged;
}
