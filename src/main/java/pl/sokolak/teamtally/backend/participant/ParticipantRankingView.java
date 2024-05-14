package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantRankingView {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private byte[] photo;
    private Integer teamId;
}
