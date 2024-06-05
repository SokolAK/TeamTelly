package pl.sokolak.teamtally.backend.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntity;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.user.User;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Event extends AbstractEntity {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean opened;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Challenge> challenges;
    private byte[] logo;
}
