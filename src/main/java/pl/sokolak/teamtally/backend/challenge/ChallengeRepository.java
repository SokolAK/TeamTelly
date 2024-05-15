package pl.sokolak.teamtally.backend.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
    List<Challenge> findAllByEvent(Event event);

    @Query(value = "SELECT c.id, c.name, c.individual_points, c.team_points " +
            "FROM challenge c " +
            "WHERE c.id IN :ids", nativeQuery = true)
    List<Map<String, Object>> findAllByIdIn(@Param("ids") Set<Integer> ids);
}