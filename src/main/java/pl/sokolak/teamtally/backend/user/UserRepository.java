package pl.sokolak.teamtally.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByEmail(String email);

    @Modifying
    @Query(value = "UPDATE Users u SET logged = true WHERE id = :id", nativeQuery = true)
    void updateLogged(@Param("id") Integer id);

    @Query(value = "SELECT u.id, u.username FROM users u " +
            "LEFT JOIN participant p ON p.user_id = u.id " +
            "WHERE NOT p.event_id = :eventId AND p.team_id IS NULL", nativeQuery = true)
    List<Map<String, Object>> findAllUnassignedByEvent(@Param("eventId") Integer eventId);
}