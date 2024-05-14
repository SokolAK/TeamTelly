package pl.sokolak.teamtally.backend.team;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.participant.ParticipantChallengeRankingView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class TeamService implements ServiceWithEvent<TeamDto> {

    private final TeamRepository teamRepository;
    private final TeamRankingRepository teamRankingRepository;
    private final Mapper mapper;

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public TeamDto save(TeamDto team) {
        Team entity = mapper.toEntity(team);
        Team savedEntity = teamRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public void delete(TeamDto team) {
        Team entity = mapper.toEntity(team);
        teamRepository.delete(entity);
    }

    @Override
    public List<TeamDto> findAllByEvent(EventDto event) {
        return teamRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Set<TeamRankingView> findAllForRankingByEvent(EventDto event) {
        return teamRankingRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(t -> new TeamRankingView(
                        (Integer) t.get("id"),
                        getStringField(t.get("icon")),
                        getStringField(t.get("name")),
                        getStringField(t.get("color")))
                ).collect(Collectors.toSet());
    }

    private String getStringField(Object value) {
        return Optional.ofNullable(value).map(String.class::cast).orElse(null);
    }
}
