package pl.sokolak.teamtally.frontend.user_section.ranking.dto;

import java.math.BigDecimal;

public record TeamWithPlace(String name,
                            String icon,
                            String color,
                            BigDecimal points,
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
