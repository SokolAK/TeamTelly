package pl.sokolak.teamtally.backend;

import java.time.LocalDate;
import java.util.List;

public interface Service<T extends Data> {

    T save(T challenge);

    void delete(T data);

    List<T> findAll();
}
