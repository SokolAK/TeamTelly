package pl.sokolak.teamtally.backend.participant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.*;

@SuperBuilder
@Getter
@Setter
public class ParticipantDto extends Data {
    private boolean active;
    private TeamDto team;
    private EventDto event;
    private UserDto user;
    @Builder.Default
    private Set<ChallengeDto> completedChallenges = new HashSet<>();
    @Builder.Default
    private List<CodeDto> usedCodes = new ArrayList<>();

    public void completeChallenge(CodeDto code) {
        usedCodes.add(code);
        completedChallenges.add(code.getChallenge());
    }

    public Integer getTeamId() {
        return Optional.ofNullable(team)
                .map(Data::getId)
                .orElse(null);
    }
}
