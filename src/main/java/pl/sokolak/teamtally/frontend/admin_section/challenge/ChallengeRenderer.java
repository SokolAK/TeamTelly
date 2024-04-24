package pl.sokolak.teamtally.frontend.admin_section.challenge;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChallengeRenderer {
    public static Renderer<ChallengeDto> create() {
        return LitRenderer.<ChallengeDto>of("""
                        <vaadin-vertical-layout>
                            <h4>${item.name}</h4>
                            <span style='margin-bottom:10px;'>${item.description}</span>
                            <vaadin-horizontal-layout style='align-items: start' theme='spacing'>
                                <vaadin-icon class='challenge-icon' icon='vaadin:user'></vaadin-icon>
                                <h5>${item.individualPoints}</h5>
                                <vaadin-icon class='challenge-icon' icon='vaadin:users'></vaadin-icon>
                                <h5>${item.teamPoints}</h5>
                            </vaadin-horizontal-layout>
                            <vaadin-horizontal-layout style='align-items: start' theme='spacing'>
                                ${item.tags.map(tag => html`<span theme='badge contrast'>${tag}</span>`)}
                            </vaadin-horizontal-layout>
                        </vaadin-vertical-layout>
                        """)
                .withProperty("name", ChallengeDto::getName)
                .withProperty("description", ChallengeDto::getDescription)
                .withProperty("individualPoints", ChallengeDto::getIndividualPoints)
                .withProperty("teamPoints", ChallengeDto::getTeamPoints)
                .withProperty("tags", c -> c.getTags().stream()
                        .map(TagDto::getName)
                        .map(name -> "#" + name)
                        .collect(Collectors.toList())
                );
    }

    public static Renderer<ChallengeDto> create(List<Integer> completedPersonal, List<Integer> completedTeam) {
        return LitRenderer.<ChallengeDto>of("""
                        <vaadin-horizontal-layout style='align-items:center;'>
                            <vaadin-vertical-layout>
                                <h3>${item.name}</h3>
                                <vaadin-horizontal-layout style='align-items:start' theme='spacing'>
                                    <vaadin-icon class='challenge-icon' icon='vaadin:user' style='color:${item.colorPersonal}'></vaadin-icon>
                                    <h5>${item.individualPoints}</h5>
                                    <vaadin-icon class='challenge-icon' icon='vaadin:users' style='color:${item.colorTeam}'></vaadin-icon>
                                    <h5>${item.teamPoints}</h5>
                                </vaadin-horizontal-layout>
                                <vaadin-horizontal-layout style='align-items:start' theme='spacing'>
                                    ${item.tags.map(tag => html`<span theme='badge contrast'>${tag}</span>`)}
                                </vaadin-horizontal-layout>
                            </vaadin-vertical-layout>
                            <div style='margin-left:auto;'>
                                <vaadin-icon icon='vaadin:check-circle' style='color:#5DAD26; width:40px; height:40px; display:${item.completed}'></vaadin-icon>
                                <vaadin-icon icon='vaadin:close-circle' style='color:#FF0000; width:40px; height:40px; display:${item.unavailable}'></vaadin-icon>
                            </div>
                        </vaadin-horizontal-layout>
                        """)
                .withProperty("name", ChallengeDto::getName)
                .withProperty("individualPoints", ChallengeDto::getIndividualPoints)
                .withProperty("teamPoints", ChallengeDto::getTeamPoints)
                .withProperty("tags", c -> c.getTags().stream()
                        .map(TagDto::getName)
                        .map(name -> "#" + name)
                        .collect(Collectors.toList()))
//                .withProperty("color", getColor(completedPersonal, "#E9FFE9", "white"))
                .withProperty("colorPersonal", getColor(completedPersonal, "#5DAD26", "#696969"))
                .withProperty("colorTeam", getColor(completedTeam, "#5DAD26", "#696969"))
                .withProperty("completed", checkIfCompleted(completedPersonal))
                .withProperty("unavailable", checkIfUnavailable(completedPersonal));
    }

    private static ValueProvider<ChallengeDto, String> checkIfCompleted(List<Integer> completed) {
        return challenge -> completed.stream()
                .filter(id -> id.equals(challenge.getId()))
                .findAny()
                .map(__ -> "block")
                .orElse("none");
    }

    private static ValueProvider<ChallengeDto, String> checkIfUnavailable(List<Integer> completed) {
        return challenge -> {
            if (completed.stream().anyMatch(id -> id.equals(challenge.getId()))) {
                return "none";
            }
            if (Optional.ofNullable(challenge.getCodes()).orElse(Collections.emptySet()).stream().noneMatch(CodeDto::isActive)) {
                return "block";
            }
            return "none";
        };
    }

    private static ValueProvider<ChallengeDto, String> getColor(List<Integer> completed, String completeColor, String defaultColor) {
        return challenge -> completed.stream()
                .filter(id -> id.equals(challenge.getId()))
                .findAny()
                .map(__ -> completeColor)
                .orElse(defaultColor);
    }
}
