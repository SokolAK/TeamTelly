package pl.sokolak.teamtally.backend.participant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    private UUID id;
    @ManyToOne
    private Event event;
    @ManyToOne
    private User user;
}
