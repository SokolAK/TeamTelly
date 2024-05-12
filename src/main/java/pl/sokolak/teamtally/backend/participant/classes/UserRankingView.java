package pl.sokolak.teamtally.backend.participant.classes;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRankingView {
    String username;
    String firstName;
    String lastName;
    String jobTitle;
    byte[] photo;
}