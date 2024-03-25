package pl.sokolak.teamtally.backend.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Mapper;
import pl.sokolak.teamtally.backend.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class UserService implements Service<UserDto> {

    private final UserRepository userRepository;
    private final Mapper userMapper;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto save(UserDto user) {
        User entity = userMapper.toEntity(user);
        User savedEntity = userRepository.save(entity);
        return userMapper.toDto(savedEntity);
    }

    public void updateWithoutPassword(UserDto user) {
//        userRepository.findById(user.getId())
//                .map(entity -> userMapper.toEntityWithPassword(user, entity.getPassword()))
//                .ifPresent(userRepository::save);
//        userRepository.findById(user.getId())
//                .map(userMapper::toDto);
    }

    @Override
    public void delete(UserDto user) {
        User entity = userMapper.toEntity(user);
        userRepository.delete(entity);
    }
}
