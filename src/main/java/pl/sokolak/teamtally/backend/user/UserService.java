package pl.sokolak.teamtally.backend.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sokolak.teamtally.abstracts.Service;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.user.role.RoleService;
import pl.sokolak.teamtally.backend.user.role.UserRoleDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class UserService implements Service<UserDto> {

    private final UserRepository userRepository;
    private final RoleService userRoleService;
    private final Mapper mapper;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .map(mapper::toDto);
    }

    @Override
    public UserDto save(UserDto user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            userRepository.findById(user.getId())
                    .map(User::getPassword)
                    .ifPresent(user::setPassword);
        } else {
            user.setPassword(encodePassword(user.getPassword()));
        }
        if (user.getUserRole() == null) {
            user.setUserRole(userRoleService.findDefault().orElse(null));
        }
        User entity = mapper.toEntity(user);
        User savedEntity = userRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public void delete(UserDto user) {
        User entity = mapper.toEntity(user);
        userRepository.delete(entity);
    }

    private String encodePassword(String password) {
        return Optional.ofNullable(password)
                .map(p -> new BCryptPasswordEncoder().encode(p))
                .orElse(null);
    }
}
