package pl.sokolak.teamtally.backend.user;

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
public class UserDto extends Data {
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String email;
    private String password;
    private UserRoleDto userRole;
    private Set<ParticipantDto> participants;
    private byte[] photo;

    public UserDto withoutPassword() {
        return UserDto.builder()
                .id(getId())
                .username(getUsername())
                .firstName(getFirstName())
                .lastName(getLastName())
                .jobTitle(getJobTitle())
                .email(getEmail())
                .userRole(getUserRole())
                .photo(getPhoto())
                .build();
    }

    public boolean isAdmin() {
        return Optional.ofNullable(userRole)
                .map(UserRoleDto::getName)
                .filter(role -> role.equalsIgnoreCase("ADMIN"))
                .isPresent();
    }

    public Optional<ParticipantDto> getParticipantForEvent(EventDto event) {
        return Optional.of(participants).orElse(Set.of())
                .stream()
                .filter(participant -> event.getId().equals(participant.getEvent().getId()))
                .findFirst();
    }

    @Override
    public String toString() {
        return username + " " + firstName + " " + lastName + " " + email;
    }
}
