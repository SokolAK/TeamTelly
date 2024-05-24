package pl.sokolak.teamtally.frontend.admin_section.challenge;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ChallengeRenderer {
    public static Renderer<ChallengeDto> create() {
        return LitRenderer.<ChallengeDto>of("""
                        <vaadin-horizontal-layout style='align-items:center;'>
                            <vaadin-vertical-layout>
                                <span style='width:100%; text-wrap:wrap;'>${item.name}</span>
                                <span style='margin-bottom:10px; white-space:wrap; font-size:small'><i>${item.description}</i></span>
                                <vaadin-horizontal-layout style='align-items: start' theme='spacing'>
                                    <div style='display: inline-block;'>
                                        <vaadin-icon class='challenge-icon' icon='vaadin:user' style='color:#696969'></vaadin-icon>
                                        <h6 style='display:inline-block; margin:0; vertical-align:middle'>⭐ ${item.individualPoints}</h6>
                                    </div>
                                    <div style='display: inline-block;'>
                                        <vaadin-icon class='challenge-icon' icon='vaadin:users' style='color:#696969'></vaadin-icon>
                                        <h6 style='display:inline-block; margin:0; vertical-align:middle'>⭐ ${item.teamPoints}</h6>
                                    </div>
                                </vaadin-horizontal-layout>
                                <vaadin-horizontal-layout style='align-items:start; margin-top:5px' theme='spacing'>
                                    ${item.tags.map(tag => html`<span theme='badge contrast'>${tag}</span>`)}
                                </vaadin-horizontal-layout>
                            </vaadin-vertical-layout>
                            <div class='right-align-div'>
                                <span style='font-size:small; text-align:center'>Codes<br>used:<br>${item.usages}</span>
                            </div>
                        </vaadin-horizontal-layout>
                        """)
                .withProperty("name", ChallengeDto::getName)
                .withProperty("description", ChallengeDto::getDescription)
                .withProperty("individualPoints", ChallengeDto::getIndividualPoints)
                .withProperty("teamPoints", ChallengeDto::getTeamPoints)
                .withProperty("usages", ChallengeRenderer::printUsages)
                .withProperty("tags", c -> c.getTags().stream()
                        .map(TagDto::getName)
                        .map(name -> "#" + name)
                        .collect(Collectors.toList())
                );
    }

    public static Renderer<ChallengeDto> create(Set<Integer> completedPersonal, Set<Integer> completedTeam) {
        return LitRenderer.<ChallengeDto>of("""
                        <vaadin-horizontal-layout style='align-items:center;'>
                            <vaadin-vertical-layout>
                                <span style='width:100%; text-wrap:wrap;'>${item.name}</span>
                                <span style='margin-bottom:5px; white-space:wrap; font-size:small'><i>${item.description}</i></span>
                                <vaadin-horizontal-layout style='align-items: start;' theme='spacing'>
                                    <div style='display: inline-block;'>
                                        <vaadin-icon class='challenge-icon' icon='vaadin:user' style='color:${item.colorPersonal}'></vaadin-icon>
                                        <h6 style='display:inline-block; margin:0; vertical-align:middle'>⭐ ${item.individualPoints}</h6>
                                    </div>
                                    <div style='display: inline-block;'>
                                        <vaadin-icon class='challenge-icon' icon='vaadin:users' style='color:${item.colorTeam}'></vaadin-icon>
                                        <h6 style='display:inline-block; margin:0; vertical-align:middle'>⭐ ${item.teamPoints}</h6>
                                    </div>
                                </vaadin-horizontal-layout>
                                <vaadin-horizontal-layout style='align-items:start; margin-top:5px' theme='spacing'>
                                    ${item.tags.map(tag => html`<span theme='badge contrast'>${tag}</span>`)}
                                </vaadin-horizontal-layout>
                            </vaadin-vertical-layout>
                            <div style='margin-left:auto;'>
                                <span style='display:${item.available?"block":"none"}; text-align:center; font-size:small'>Codes<br>left:<br>${item.usagesLeft}</span>
                                <vaadin-icon icon='vaadin:check-circle' style='color:#5DAD26; width:25px; height:25px; display:${item.completed?"block":"none"}'></vaadin-icon>
                                <vaadin-icon icon='vaadin:close-circle' style='color:#FF0000; width:25px; height:25px; display:${item.unavailable?"block":"none"}'></vaadin-icon>
                            </div>
                        </vaadin-horizontal-layout>
                        """)
                .withProperty("name", ChallengeDto::getName)
                .withProperty("description", ChallengeDto::getDescription)
                .withProperty("individualPoints", ChallengeDto::getIndividualPoints)
                .withProperty("teamPoints", ChallengeDto::getTeamPoints)
                .withProperty("usagesLeft", ChallengeRenderer::printUsagesLeft)
                .withProperty("tags", c -> c.getTags().stream()
                        .map(TagDto::getName)
                        .map(name -> "#" + name)
                        .collect(Collectors.toList()))
//                .withProperty("color", getColor(completedPersonal, "#E9FFE9", "white"))
                // TODO
                .withProperty("colorPersonal", getColor(completedPersonal, "#5DAD26", "#696969"))
//                .withProperty("colorPersonal", __ -> "#696969")
                // TODO
                .withProperty("colorTeam", getColor(completedTeam, "#5DAD26", "#696969"))
//                .withProperty("colorTeam", __ -> "#696969")
                .withProperty("completed", checkIfCompleted(completedPersonal))
                .withProperty("available", checkIfAvailable(completedPersonal))
                .withProperty("unavailable", checkIfUnavailable(completedPersonal))
                ;
    }

    private static ValueProvider<ChallengeDto, Boolean> checkIfCompleted(Set<Integer> completed) {
        return challenge -> isCompleted(challenge, completed);
    }

    private static ValueProvider<ChallengeDto, Boolean> checkIfAvailable(Set<Integer> completed) {
        return challenge -> !isCompleted(challenge, completed) && isActive(challenge);
    }

    private static ValueProvider<ChallengeDto, Boolean> checkIfUnavailable(Set<Integer> completed) {
        return challenge -> !isCompleted(challenge, completed) && !isActive(challenge);
    }

    private static boolean isCompleted(ChallengeDto challenge, Set<Integer> completed) {
        return completed.stream()
                .anyMatch(id -> id.equals(challenge.getId()));
    }

    private static boolean isActive(ChallengeDto challenge) {
        return Optional.ofNullable(challenge.getCodes()).orElse(Collections.emptySet()).stream().anyMatch(CodeDto::isActive);
    }

    private static ValueProvider<ChallengeDto, String> getColor(Set<Integer> completed, String completeColor, String defaultColor) {
        return challenge -> completed.contains(challenge.getId()) ? completeColor : defaultColor;
    }

    private static String printUsagesLeft(ChallengeDto challenge) {
        Usages usages = calculateUsages(challenge);
        if (usages.isInfinitelyReusable()) {
            return "∞";
        } else {
            return String.valueOf(usages.getMaxUsages() - usages.getUsages());
        }
    }

    private static String printUsages(ChallengeDto challenge) {
        Usages usages = calculateUsages(challenge);
        if (usages.isInfinitelyReusable()) {
            return usages.getUsages() + "/∞";
        } else {
            return usages.getUsages() + "/" + usages.getMaxUsages();
        }
    }

    private static Usages calculateUsages(ChallengeDto challenge) {
        int usages = 0;
        Integer maxUsages = 0;
        for (CodeDto code : challenge.getCodes()) {
            usages += code.getUsages();
            if (code.getMaxUsages() == null) {
                maxUsages = null;
            }
            if (maxUsages != null) {
                maxUsages += code.getMaxUsages();
            }
        }
        return new Usages(usages, maxUsages);
    }

    @AllArgsConstructor
    @Getter
    private static class Usages {
        private int usages;
        private Integer maxUsages;

        boolean isInfinitelyReusable() {
            return maxUsages == null;
        }

        ;
    }
}
