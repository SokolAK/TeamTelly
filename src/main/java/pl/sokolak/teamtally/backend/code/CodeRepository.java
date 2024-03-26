package pl.sokolak.teamtally.backend.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    List<Code> findAllByEvent(Event event);
}