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

    @Query(value = "SELECT pc.participant_id AS participantId, cd.challenge_id AS challengeId FROM participant_code pc " +
            "JOIN code cd ON cd.id = pc.code_id " +
            "JOIN participant p ON pc.participant_id = p.id " +
            "WHERE p.active = true AND p.event_id = :eventId", nativeQuery = true)
    List<Map<String, Integer>> getParticipantChallenges(@Param("eventId") int eventId);

    @Query(value = "SELECT p.id, username " +
            "FROM participant p " +
            "JOIN participant_code pc ON pc.participant_id = p.id " +
            "JOIN code c ON c.id = pc.code_id " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE c.id = :codeId", nativeQuery = true)
    List<Map<String, Object>> getAllUsernamesByCode(@Param("codeId") int codeId);

    @Query(value = "SELECT p.id, username " +
            "FROM participant p " +
            "JOIN participant_code pc ON pc.participant_id = p.id " +
            "JOIN code c ON c.id = pc.code_id " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE c.challenge_id = :challengeId", nativeQuery = true)
    List<Map<String, Object>> getAllUsernamesByChallenge(@Param("challengeId") int challengeId);

    @Modifying
    @Query(value = "UPDATE Participant p SET team_id = :teamId WHERE id = :id", nativeQuery = true)
    void updateTeam(@Param("id") Integer id, @Param("teamId") Integer teamId);

    @Modifying
    @Query(value = "UPDATE Participant p SET active = :isActive WHERE id = :id", nativeQuery = true)
    void updateActive(@Param("id") Integer id, @Param("isActive") boolean isActive);

    @Modifying
    @Query(value = "INSERT INTO participant_code(participant_id, code_id) VALUES (:id, :codeId)", nativeQuery = true)
    void updateCode(@Param("id") Integer id, @Param("codeId") Integer codeId);

//    @Modifying
//    @Query(value = "INSERT INTO participant_challenge(participant_id, challenge_id) VALUES (:id, :challengeId)", nativeQuery = true)
//    void updateChallenge(@Param("id") Integer id, @Param("challengeId") Integer challengeId);
}
