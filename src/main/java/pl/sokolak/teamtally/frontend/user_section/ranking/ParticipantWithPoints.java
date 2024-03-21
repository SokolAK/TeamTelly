package pl.sokolak.teamtally.frontend.user_section.ranking;

public record ParticipantWithPoints(String username,
                                    String firstName,
                                    String lastName,
                                    String teamIcon,
                                    String teamName,
                                    String teamColor,
                                    int points) {
}
