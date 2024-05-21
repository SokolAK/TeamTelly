package pl.sokolak.teamtally.backend.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.role.UserRoleDto;

import java.util.Optional;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserDataView extends Data {
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String email;
    private String password;
    private UserRoleDto userRole;
    private byte[] photo;
    private boolean logged;
}
