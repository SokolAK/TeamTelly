package pl.sokolak.teamtally.backend.calculator;

import pl.sokolak.teamtally.backend.participant.ParticipantDto;

public class PointsCalculator {

    public int calculate(ParticipantDto participant) {
        return participant.getUser().getUsername().charAt(0);
    }
}
