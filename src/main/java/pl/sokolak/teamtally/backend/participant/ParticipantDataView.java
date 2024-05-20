package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class ParticipantDataView {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private byte[] photo;
    private Integer teamId;
    private Boolean isActive;
}
