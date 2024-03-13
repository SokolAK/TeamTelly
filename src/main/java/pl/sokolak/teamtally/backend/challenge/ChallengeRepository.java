package pl.sokolak.teamtally.backend.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {
    List<Challenge> findAllByEvent(Event event);
}