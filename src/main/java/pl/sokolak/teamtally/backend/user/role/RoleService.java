package pl.sokolak.teamtally.backend.user.role;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sokolak.teamtally.backend.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final Mapper mapper;

    public List<UserRoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserRoleDto> findDefault() {
        return roleRepository.findFirstByIsDefault(true)
                .map(mapper::toDto);
    }
}
