package pl.sokolak.teamtally.backend.user;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .map(userMapper::toDto);
    }

    public void save(UserDto user) {
        User entity = userMapper.toEntity(user);
        User savedEntity = userRepository.save(entity);
        userMapper.toDto(savedEntity);
    }

    public void updateWithoutPassword(UserDto user) {
        userRepository.findById(user.getId())
                .map(entity -> userMapper.toEntityWithPassword(user, entity.getPassword()))
                .ifPresent(userRepository::save);
        userRepository.findById(user.getId())
                .map(userMapper::toDto);
    }

    public void delete(UserDto user) {
        User entity = userMapper.toEntity(user);
        userRepository.delete(entity);
    }
}
