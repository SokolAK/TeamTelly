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
@Table(name = "user", schema = "public")
public class User extends AbstractEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    private UserRole userRole;
    @OneToMany(mappedBy = "user")
    private Set<Participant> participants;
}
