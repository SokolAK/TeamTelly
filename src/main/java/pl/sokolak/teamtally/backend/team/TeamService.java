package pl.sokolak.teamtally.backend.team;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Service;
import pl.sokolak.teamtally.backend.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class TeamService implements ServiceWithEvent<TeamDto> {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public TeamDto save(TeamDto team) {
        Team entity = teamMapper.toEntity(team);
        Team savedEntity = teamRepository.save(entity);
        return teamMapper.toDto(savedEntity);
    }

    public void delete(TeamDto team) {
        Team entity = teamMapper.toEntity(team);
        teamRepository.delete(entity);
    }

    @Override
    public List<TeamDto> findAllByEvent(EventDto event) {
        return teamRepository.findAllByEvent(new EventMapper().toEntity(event)).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }
}
