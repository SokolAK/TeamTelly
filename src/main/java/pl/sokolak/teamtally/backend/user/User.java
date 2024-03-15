package pl.sokolak.teamtally.backend.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.participant.Participant;
import pl.sokolak.teamtally.backend.user.role.UserRole;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "public")
public class User {
    @Id
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToOne
    private UserRole userRole;
    @OneToMany(mappedBy = "user")
    private Set<Participant> participants;
}
