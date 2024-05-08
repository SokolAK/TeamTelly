package pl.sokolak.teamtally.backend.suggestion;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;

@SuperBuilder
@Getter
@Setter
public class SuggestionDto extends Data {
    private UserDto user;
    private String text;
}
