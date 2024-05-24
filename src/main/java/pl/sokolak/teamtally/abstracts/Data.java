package pl.sokolak.teamtally.abstracts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@SuperBuilder
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Data {
    protected Integer id;
}
