package pl.sokolak.teamtally.backend.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate date1, LocalDate date2);
}