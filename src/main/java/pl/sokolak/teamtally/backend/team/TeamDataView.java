package pl.sokolak.teamtally.backend.team;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TeamDataView {
    private Integer id;
    private String icon;
    private String name;
    private String color;
}
