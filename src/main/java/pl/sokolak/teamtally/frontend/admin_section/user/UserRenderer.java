package pl.sokolak.teamtally.frontend.admin_section.user;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.util.ImageUtil;

import java.util.Base64;

public class UserRenderer {
    public static Renderer<UserDto> create() {
        return LitRenderer.<UserDto>of("""
                        <vaadin-horizontal-layout>
                            <div class='user-photo-div'>
                                <img class='user-photo-medium' src=${item.photo} alt=${item.name}>
                            </div>
                            <vaadin-vertical-layout style='margin-left: 10px'>
                                <span><b>${item.username}</b> <span theme='badge ${item.roleColor}'>${item.role}</span></span>
                                <span style='text-wrap:balance'>${item.firstName} ${item.lastName}</span>
                                <span style='text-wrap:balance'>${item.jobTitle}</span>
                                <span><i>${item.email}</i></span>
                            </vaadin-vertical-layout>
                            <div class='right-align-div'>
                                <vaadin-icon icon='vaadin:user-check' style='color:#5DAD26; width:30px; height:30px; display:${item.logged}'></vaadin-icon>
                            </div>
                        </vaadin-horizontal-layout>
                        """)
                .withProperty("username", UserDto::getUsername)
                .withProperty("firstName", UserDto::getFirstName)
                .withProperty("lastName", UserDto::getLastName)
                .withProperty("jobTitle", UserDto::getJobTitle)
                .withProperty("email", UserDto::getEmail)
                .withProperty("role", u -> u.getUserRole().getName())
                .withProperty("roleColor", u -> u.isAdmin() ? "success" : "contrast")
                .withProperty("photo", u -> ImageUtil.createUserPhotoAsBase64(u.getPhoto()))
                .withProperty("logged", u -> u.isLogged() ? "block" : "none");
    }
}
