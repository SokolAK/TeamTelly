package pl.sokolak.teamtally.frontend.admin_section.suggestion;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.util.ImageUtil;

import java.time.LocalDate;
import java.util.Optional;

public class SuggestionRenderer {
    public static Renderer<SuggestionDto> create() {
        return LitRenderer.<SuggestionDto>of("""
                        <vaadin-horizontal-layout>
                            <div class='user-photo-div'>
                                <img class='user-photo-medium' src=${item.photo} alt=${item.name}>
                            </div>
                            <vaadin-vertical-layout style='margin-left: 10px'>
                                <span style='text-wrap:balance'><b>${item.username}</b></span>
                                <span style='text-wrap:balance'>${item.firstName} ${item.lastName}</span>
                                <span style='text-wrap:balance'><i>${item.eventName} ${item.eventStartDate}-${item.eventEndDate}</i></span>
                            </vaadin-vertical-layout>
                        </vaadin-horizontal-layout>
                        """)
//                .withProperty("text", SuggestionDto::getText)
                .withProperty("username", SuggestionRenderer::getUserName)
                .withProperty("firstName", SuggestionRenderer::getFirstName)
                .withProperty("lastName", SuggestionRenderer::getLastName)
                .withProperty("photo", SuggestionRenderer::getUserPhoto)
                .withProperty("eventName", SuggestionRenderer::getEventName)
                .withProperty("eventStartDate", SuggestionRenderer::getEventStartDate)
                .withProperty("eventEndDate", SuggestionRenderer::getEventEndDate);
    }

    private static String getUserName(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getUser())
                .map(UserDto::getUsername)
                .orElse("");
    }

    private static String getFirstName(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getUser())
                .map(UserDto::getFirstName)
                .orElse("");
    }

    private static String getLastName(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getUser())
                .map(UserDto::getLastName)
                .orElse("");
    }

    private static String getUserPhoto(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getUser())
                .map(UserDto::getPhoto)
                .map(ImageUtil::createUserPhotoAsBase64)
                .orElse("");
    }

    private static String getEventName(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getEvent())
                .map(EventDto::getName)
                .orElse("");
    }

    private static String getEventStartDate(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getEvent())
                .map(EventDto::getStartDate)
                .map(LocalDate::toString)
                .orElse("");
    }

    private static String getEventEndDate(SuggestionDto suggestion) {
        return Optional.ofNullable(suggestion.getEvent())
                .map(EventDto::getEndDate)
                .map(LocalDate::toString)
                .orElse("");
    }
}
