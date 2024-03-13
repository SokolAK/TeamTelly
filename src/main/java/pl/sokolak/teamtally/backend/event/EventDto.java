package pl.sokolak.teamtally.backend.event;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDto implements Data {
    @Id
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private UserDto owner;
}
