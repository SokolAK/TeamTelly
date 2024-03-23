package pl.sokolak.teamtally.backend.tag;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.Data;

import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
public class TagDto extends Data {
    private UUID id;
    private String name;
}
