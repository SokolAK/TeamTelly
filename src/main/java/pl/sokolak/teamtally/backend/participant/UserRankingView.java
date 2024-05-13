package pl.sokolak.teamtally.backend.participant;

public interface UserRankingView {
    String getUsername();
    String getFirstName();
    String getLastName();
    String getJobTitle();
    byte[] getPhoto();
}