package pl.sokolak.teamtally.backend.team;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TeamService {

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
}
