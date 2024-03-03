package pl.sokolak.teamtelly.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    private UUID id;
    private String name;
    private Integer individualPoints;
    private Integer teamPoints;
}
