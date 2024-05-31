package pl.sokolak.teamtally.backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public class CustomValidator<T> {
    private Predicate<T> condition;
    private Function<T, String> message;
}