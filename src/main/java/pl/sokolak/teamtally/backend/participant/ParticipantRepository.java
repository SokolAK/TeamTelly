package pl.sokolak.teamtally.backend.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.user.User;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> findAllByUser(User user);
    List<Participant> findAllByEvent(Event event);
    List<Participant> findAllByEventAndActive(Event event, boolean active);
//    List<ParticipantRankingView> getAllByEventAndActive(Event event, boolean active);
    <T> List<T> getAllByEventAndActive(Event event, boolean active, Class<T> type);
    void deleteAllByUser(User user);
}