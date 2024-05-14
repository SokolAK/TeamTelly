package pl.sokolak.teamtally.backend.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TeamRankingView {
    private Integer id;
    private String icon;
    private String name;
    private String color;
}
