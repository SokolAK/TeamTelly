package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantRankingDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private byte[] photo;
    private String icon;
    private String name;
    private String color;
}
