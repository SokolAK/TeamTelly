package pl.sokolak.teamtally.abstracts;

import java.util.List;
import java.util.Optional;

public interface Service<T extends Data> {

    T save(T data);

    void delete(T data);

    List<T> findAll();

    default String getStringField(Object value) {
        return Optional.ofNullable(value).map(String.class::cast).orElse(null);
    }
}
