package pl.sokolak.teamtally.frontend.user_section.ranking.dto;

import pl.sokolak.teamtally.backend.team.TeamDto;

import java.math.BigDecimal;

public record TeamWithPoints(String name,
                             String icon,
                             String color,
                             BigDecimal points) {

    public TeamWithPoints(TeamDto team, BigDecimal points) {
        this(team.getName(),
                team.getIcon(),
                team.getColor(),
                points
        );
    }
}
