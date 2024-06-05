package pl.sokolak.teamtally.backend.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

    List<Challenge> findAllByEvent(Event event);

    @Query(value = "SELECT ch.id AS challenge_id, ch.name AS challenge_name, ch.description, ch.individual_points, ch.team_points, " +
            "co.id AS code_id, co.code, co.active, co.usages, co.max_usages, co.code_from, " +
            "t.id, t.name AS tag_name " +
            "FROM challenge ch " +
            "LEFT JOIN code co ON co.challenge_id = ch.id " +
            "LEFT JOIN challenge_tag ct ON ct.challenge_id = ch.id " +
            "LEFT JOIN tag t ON t.id = ct.tag_id " +
            "WHERE ch.event_id = :id", nativeQuery = true)
    List<Map<String, Object>> findAllByEventId(@Param("id") Integer id);

    @Query(value = "SELECT c.id, c.name, c.individual_points, c.team_points " +
            "FROM challenge c " +
            "WHERE c.id IN :ids", nativeQuery = true)
    List<Map<String, Object>> findAllByIdIn(@Param("ids") Set<Integer> ids);


    @Query(value = "SELECT c.challenge_id as id " +
            "FROM participant_code pc " +
            "JOIN code c ON c.id = pc.code_id " +
            "WHERE pc.participant_id = :id", nativeQuery = true)
    List<Map<String, Object>> findAllIdsCompletedByParticipant(@Param("id") Integer id);


    @Query(value = "SELECT c.challenge_id as id " +
            "FROM participant_code pc " +
            "         LEFT JOIN code c ON c.id = pc.code_id " +
            "         LEFT JOIN participant p ON p.id = pc.participant_id " +
            "         LEFT JOIN team t ON t.id = p.team_id " +
            "WHERE t.id = :teamId " +
            "GROUP BY c.challenge_id " +
            "HAVING COUNT(c.challenge_id) = " +
            "   (SELECT COUNT(sp.id) " +
            "    FROM participant sp " +
            "    WHERE sp.team_id = :teamId)", nativeQuery = true)
    List<Map<String, Object>> findAllIdsCompletedByTeam(@Param("teamId") Integer teamId);
}
