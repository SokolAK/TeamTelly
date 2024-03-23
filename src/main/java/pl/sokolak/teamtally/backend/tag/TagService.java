package pl.sokolak.teamtally.backend.tag;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Service;
import pl.sokolak.teamtally.backend.ServiceWithEvent;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class TagService implements Service<TagDto> {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto save(TagDto challenge) {
        Tag entity = tagMapper.toEntity(challenge);
        Tag savedEntity = tagRepository.save(entity);
        return tagMapper.toDto(savedEntity);
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(TagDto challenge) {
        Tag entity = tagMapper.toEntity(challenge);
        tagRepository.delete(entity);
    }
}
