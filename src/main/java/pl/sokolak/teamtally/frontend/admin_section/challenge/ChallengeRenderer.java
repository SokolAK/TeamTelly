package pl.sokolak.teamtally.frontend.admin_section.challenge;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.List;
import java.util.stream.Collectors;

public class ChallengeRenderer {
    public static Renderer<ChallengeDto> create() {
        return LitRenderer.<ChallengeDto>of("<vaadin-vertical-layout>"
                        + "<h3>${item.name}</h3>"
                        + "<vaadin-horizontal-layout style='align-items: start' theme='spacing'>"
                        + "  <vaadin-icon class='challenge-icon' icon='vaadin:user'></vaadin-icon>"
                        + "  <h5>${item.personalPoints}</h5>"
                        + "  <vaadin-icon class='challenge-icon' icon='vaadin:users'></vaadin-icon>"
                        + "  <h5>${item.teamPoints}</h5>"
                        + "</vaadin-horizontal-layout>"
                        + "<vaadin-horizontal-layout style='align-items: start' theme='spacing'>"
                        + "${item.tags.map(tag => html`<span theme=\"badge contrast\">${tag}</span>`)}"
                        + "</vaadin-horizontal-layout>"
                        + "</vaadin-vertical-layout>")
                .withProperty("name", ChallengeDto::getName)
                .withProperty("personalPoints", ChallengeDto::getPersonalPoints)
                .withProperty("teamPoints", ChallengeDto::getTeamPoints)
                .withProperty("tags", c -> c.getTags().stream()
                        .map(TagDto::getName)
                        .map(name -> "#" + name)
                        .collect(Collectors.toList())
                );
    }

    public static Renderer<ChallengeDto> create(List<Integer> completedPersonal, List<Integer> completedTeam) {
        return LitRenderer.<ChallengeDto>of(
                        "<vaadin-horizontal-layout style='align-items:center;'>"
                                + "<vaadin-vertical-layout>"
                                + "<h3>${item.name}</h3>"
                                + "<vaadin-horizontal-layout style='align-items:start' theme='spacing'>"
                                + "  <vaadin-icon class='challenge-icon' icon='vaadin:user' style='color:${item.colorPersonal}'></vaadin-icon>"
                                + "  <h5>${item.personalPoints}</h5>"
                                + "  <vaadin-icon class='challenge-icon' icon='vaadin:users' style='color:${item.colorTeam}'></vaadin-icon>"
                                + "  <h5>${item.teamPoints}</h5>"
                                + "</vaadin-horizontal-layout>"
                                + "<vaadin-horizontal-layout style='align-items:start' theme='spacing'>"
                                + "${item.tags.map(tag => html`<span theme='badge contrast'>${tag}</span>`)}"
                                + "</vaadin-horizontal-layout>"
                                + "</vaadin-vertical-layout>"
                                + "<div style='margin-left:auto; visibility:${item.visibility}'>"
                                + "<vaadin-icon icon='vaadin:check-circle' style='color:#5DAD26; width:40px; height:40px'></vaadin-icon>"
                                + "</div>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("name", ChallengeDto::getName)
                .withProperty("personalPoints", ChallengeDto::getPersonalPoints)
                .withProperty("teamPoints", ChallengeDto::getTeamPoints)
                .withProperty("tags", c -> c.getTags().stream()
                        .map(TagDto::getName)
                        .map(name -> "#" + name)
                        .collect(Collectors.toList()))
//                .withProperty("color", getColor(completedPersonal, "#E9FFE9", "white"))
                .withProperty("colorPersonal", getColor(completedPersonal, "#5DAD26", "#696969"))
                .withProperty("colorTeam", getColor(completedTeam, "#5DAD26", "#696969"))
                .withProperty("visibility", getVisible(completedPersonal));
    }

    private static ValueProvider<ChallengeDto, String> getVisible(List<Integer> completed) {
        return challenge -> completed.stream()
                .filter(id -> id.equals(challenge.getId()))
                .findAny()
                .map(__ -> "visible")
                .orElse("hidden");
    }

    private static ValueProvider<ChallengeDto, String> getColor(List<Integer> completed, String completeColor, String defaultColor) {
        return challenge -> completed.stream()
                .filter(id -> id.equals(challenge.getId()))
                .findAny()
                .map(__ -> completeColor)
                .orElse(defaultColor);
    }
}
