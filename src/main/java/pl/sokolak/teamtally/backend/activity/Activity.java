package pl.sokolak.teamtally.backend.activity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
}
