package pl.sokolak.teamtelly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sokolak.backend.domain.Activity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
//    List<Activity> findAll();
    Optional<Activity> findById(UUID id);
}