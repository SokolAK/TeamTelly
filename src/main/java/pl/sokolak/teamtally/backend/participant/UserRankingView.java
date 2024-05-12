package pl.sokolak.teamtally.backend.participant;

import jakarta.persistence.Table;

@Table(name = "users")
public interface UserRankingView {
    String getUsername();
    String getFirstName();
    String getLastName();
    String getJobTitle();
    byte[] getPhoto();
}