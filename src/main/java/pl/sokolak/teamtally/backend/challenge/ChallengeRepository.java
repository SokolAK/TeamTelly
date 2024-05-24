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


    @Query(value = "SELECT pc.challenge_id as id " +
            "FROM participant_challenge pc " +
            "WHERE pc.participant_id = :id", nativeQuery = true)
    List<Map<String, Object>> findAllIdsCompletedByParticipant(@Param("id") Integer id);


    @Query(value = "SELECT pc.challenge_id as id " +
            "FROM participant_challenge pc " +
            "         LEFT JOIN participant p ON p.id = pc.participant_id " +
            "         LEFT JOIN team t ON t.id = p.team_id " +
            "WHERE t.id = :teamId " +
            "GROUP BY pc.challenge_id " +
            "HAVING COUNT(pc.challenge_id) = " +
            "   (SELECT COUNT(sp.id) " +
            "    FROM participant sp " +
            "    WHERE sp.team_id = :teamId)", nativeQuery = true)
    List<Map<String, Object>> findAllIdsCompletedByTeam(@Param("teamId") Integer teamId);
}
