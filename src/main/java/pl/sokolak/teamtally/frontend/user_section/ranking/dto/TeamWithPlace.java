package pl.sokolak.teamtally.frontend.user_section.ranking.dto;

import java.util.HashSet;
import java.util.Set;

public record TeamWithPlace(String name,
                            String icon,
                            String color,
                            int points,
                            int place) {

    public TeamWithPlace(TeamWithPoints team, int place) {
        this(team.name(),
                team.icon(),
                team.color(),
                team.points(),
                place
        );
    }
}
