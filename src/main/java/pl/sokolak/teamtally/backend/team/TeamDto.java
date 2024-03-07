package pl.sokolak.teamtally.backend.team;

import lombok.*;

import java.util.UUID;

//@Getter
//@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class TeamDto {
//    private UUID id;
//    private String name;
//    private String color;
//    private String icon;
//}

@Builder
public record TeamDto(UUID id, String name, String color, String icon) {}

