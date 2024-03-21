package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.user.UserDto;

public class ParticipantRenderer {
    public static Renderer<UserDto> create() {
        return LitRenderer.<UserDto>of("<vaadin-vertical-layout>"
                        + "<h5>${item.username}</h5>"
                        + "<h7><b>${item.firstName} ${item.lastName}</b></h7>"
                        + "<h7><i>${item.email}</i></h7>"
                        + "</vaadin-vertical-layout>")
                .withProperty("username", UserDto::getUsername)
                .withProperty("firstName", UserDto::getFirstName)
                .withProperty("lastName", UserDto::getLastName)
                .withProperty("email", UserDto::getEmail);
    }
}
