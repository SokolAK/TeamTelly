package pl.sokolak.teamtally.backend.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.Map;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findAllByEvent(Event event);
}