package pl.sokolak.teamtally.backend.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.Map;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    List<Code> findAllByEvent(Event event);

    @Query(value = "SELECT c.id AS code_id, c.code, c.active, c.usages, c.max_usages, " +
            "ch.id as challenge_id, ch.name, ch.individual_points, ch.team_points " +
            "FROM code c " +
            "LEFT JOIN challenge ch ON ch.id = challenge_id " +
            "WHERE c.event_id = :eventId", nativeQuery = true)
    List<Map<String, Object>> findAllByEvent(@Param("eventId") Integer eventId);
}
