package pl.sokolak.teamtally.backend.history;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;

import java.time.Instant;

@SuperBuilder
@Getter
@Setter
public class HistoryDto extends Data {
    private Instant timestamp;
    private String username;
    private String operation;
}
