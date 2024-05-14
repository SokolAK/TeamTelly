package pl.sokolak.teamtally.backend.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ParticipantRankingRepository extends JpaRepository<Participant, Integer> {

    @Query(value = "SELECT p.id, username, first_name, last_name, job_title, photo, team_id " +
            "FROM participant p " +
            "JOIN users u ON p.user_id = u.id " +
            "LEFT JOIN team t ON p.team_id = t.id " +
            "WHERE p.active = true AND p.event_id = :eventId", nativeQuery = true)
    List<Map<String, Object>> getAllByEvent(@Param("eventId") int eventId);

    @Query(value = "SELECT pc.participant_id AS participantId, pc.challenge_id AS challengeId FROM participant_challenge pc " +
            "JOIN participant p ON pc.participant_id = p.id " +
            "JOIN challenge c ON pc.challenge_id = c.id " +
            "WHERE p.active = true AND p.event_id = :eventId", nativeQuery = true)
    List<Map<String, Integer>> getParticipantChallenges(@Param("eventId") int eventId);
}