package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChallengeDataView {
    private Integer id;
    private String name;
    private Integer individualPoints;
    private Integer teamPoints;
}
