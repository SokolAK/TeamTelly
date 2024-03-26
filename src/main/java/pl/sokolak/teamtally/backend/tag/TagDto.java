package pl.sokolak.teamtally.backend.tag;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
public class TagDto extends Data {
    private String name;
}
