package pl.sokolak.teamtally.backend.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Challenge> challenges;
}
