package pl.sokolak.teamtally.backend.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<Code, UUID> {
    List<Code> findAllByEvent(Event event);
}