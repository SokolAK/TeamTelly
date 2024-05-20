package pl.sokolak.teamtally.backend.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.user.User;

import java.util.List;
import java.util.Map;


@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> findAllByUser(User user);

    List<Participant> findAllByEvent(Event event);

    @Query(value = "SELECT p.id, username, first_name, last_name, job_title, photo, team_id, active " +
            "FROM participant p " +
            "JOIN users u ON p.user_id = u.id " +
            "LEFT JOIN team t ON p.team_id = t.id " +
            "WHERE p.event_id = :eventId", nativeQuery = true)
    List<Map<String, Object>> getAllByEvent(@Param("eventId") int eventId);

    @Query(value = "SELECT p.id, username, first_name, last_name, job_title, photo, team_id, active " +
            "FROM participant p " +
            "JOIN users u ON p.user_id = u.id " +
            "LEFT JOIN team t ON p.team_id = t.id " +
            "WHERE p.active = true AND p.event_id = :eventId", nativeQuery = true)
    List<Map<String, Object>> getAllActiveByEvent(@Param("eventId") int eventId);

    @Query(value = "SELECT pc.participant_id AS participantId, pc.challenge_id AS challengeId FROM participant_challenge pc " +
            "JOIN participant p ON pc.participant_id = p.id " +
            "JOIN challenge c ON pc.challenge_id = c.id " +
            "WHERE p.active = true AND p.event_id = :eventId", nativeQuery = true)
    List<Map<String, Integer>> getParticipantChallenges(@Param("eventId") int eventId);

    @Query(value = "SELECT p.id, username " +
            "FROM participant p " +
            "JOIN participant_code pc ON pc.participant_id = p.id " +
            "JOIN code c ON c.id = pc.code_id " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE c.id = :codeId", nativeQuery = true)
    List<Map<String, Object>> getAllUsernamesByCode(@Param("codeId") int codeId);

    @Modifying
    @Query(value = "UPDATE Participant p SET team_id = :teamId WHERE id = :id", nativeQuery = true)
    void updateTeam(@Param("id") Integer id, @Param("teamId") Integer teamId);

    @Modifying
    @Query(value = "UPDATE Participant p SET active = :isActive WHERE id = :id", nativeQuery = true)
    void updateActive(@Param("id") Integer id, @Param("isActive") boolean isActive);
}