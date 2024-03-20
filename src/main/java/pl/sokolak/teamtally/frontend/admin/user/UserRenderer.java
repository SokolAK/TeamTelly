package pl.sokolak.teamtally.frontend.admin.user;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.UserDto;

public class UserRenderer {
    public static Renderer<UserDto> create() {
        return LitRenderer.<UserDto>of("<vaadin-vertical-layout>"
                        + "<h4>${item.username} <span theme=\"badge ${item.roleColor}\">${item.role}</span></h4>"
                        + "<h7><b>${item.firstName} ${item.lastName}</b></h7>"
                        + "<h7><i>${item.email}</i></h7>"
                        + "</vaadin-vertical-layout>")
                .withProperty("username", UserDto::getUsername)
                .withProperty("firstName", UserDto::getFirstName)
                .withProperty("lastName", UserDto::getLastName)
                .withProperty("email", UserDto::getEmail)
                .withProperty("role", u -> u.getUserRole().getName())
                .withProperty("roleColor", u -> u.isAdmin() ? "success" : "contrast");
    }
}
