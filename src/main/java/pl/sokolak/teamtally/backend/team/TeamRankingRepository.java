package pl.sokolak.teamtally.backend.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.Map;

@Repository
public interface TeamRankingRepository extends JpaRepository<Team, Integer> {

    @Query(value = "SELECT t.id, t.icon, t.name, t.color " +
            "FROM Team t " +
            "WHERE t.event_id = :eventId", nativeQuery = true)
    List<Map<String, Object>> findAllByEvent(Event event);
}