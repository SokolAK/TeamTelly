package pl.sokolak.teamtally.backend.tag;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.Service;
import pl.sokolak.teamtally.backend.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class TagService implements Service<TagDto> {

    private final TagRepository tagRepository;
    private final Mapper mapper;

    @Override
    public TagDto save(TagDto tag) {
        Tag entity = mapper.toEntity(tag);
        Tag savedEntity = tagRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(TagDto tag) {
        Tag entity = mapper.toEntity(tag);
        tagRepository.delete(entity);
    }
}
