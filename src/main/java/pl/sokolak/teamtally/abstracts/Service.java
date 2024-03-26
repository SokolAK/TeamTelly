package pl.sokolak.teamtally.abstracts;

import java.util.List;

public interface Service<T extends Data> {

    T save(T challenge);

    void delete(T data);

    List<T> findAll();
}
