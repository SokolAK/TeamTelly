package pl.sokolak.teamtally.backend.user.role;

import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserMapper;
import pl.sokolak.teamtally.backend.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<UserRole> findAll() {
        return roleRepository.findAll();
    }
}
