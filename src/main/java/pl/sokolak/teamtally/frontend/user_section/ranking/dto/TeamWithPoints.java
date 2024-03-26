package pl.sokolak.teamtally.frontend.user_section.ranking.dto;

import pl.sokolak.teamtally.backend.team.TeamDto;

public record TeamWithPoints(String name,
                             String icon,
                             String color,
                             int points) {

    public TeamWithPoints(TeamDto team, int points) {
        this(team.getName(),
                team.getIcon(),
                team.getColor(),
                points
        );
    }
}
