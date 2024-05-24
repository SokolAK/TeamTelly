package pl.sokolak.teamtally.backend.suggestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sokolak.teamtally.backend.code.Code;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.Map;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {

    @Query(value = "SELECT s.id, s.user_id, s.event_id, s.text FROM suggestion s", nativeQuery = true)
    List<Map<String, Object>> findAllData();
}