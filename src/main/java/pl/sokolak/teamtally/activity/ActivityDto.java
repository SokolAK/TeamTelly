package pl.sokolak.teamtally.activity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityDto {
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
}
