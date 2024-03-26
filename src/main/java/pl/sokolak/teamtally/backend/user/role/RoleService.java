package pl.sokolak.teamtally.backend.user.role;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<UserRoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(UserRoleDto::new)
                .collect(Collectors.toList());
    }
}
