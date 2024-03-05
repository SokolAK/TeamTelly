package pl.sokolak.teamtally.team;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamDto {
    private UUID id;
    private String name;
    private String color;
    private String icon;
}
