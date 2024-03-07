package pl.sokolak.teamtally.backend.activity;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityDto {
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
}
