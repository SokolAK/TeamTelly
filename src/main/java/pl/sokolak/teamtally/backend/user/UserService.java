package pl.sokolak.teamtally.backend.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sokolak.teamtally.abstracts.Service;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.role.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public List<UserDto> findAllData() {
        return userRepository.findAll().stream()
                .map(u -> mapWithoutParticipantAndPhoto(u)
                        .photo(u.getPhoto())
                        .build())
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllDataByIds(Set<Integer> ids) {
        return userRepository.findAllById(ids).stream()
                .map(u -> mapWithoutParticipantAndPhoto(u)
                        .photo(u.getPhoto())
                        .build())
                .collect(Collectors.toList());
    }

    public Set<UserDto> findAllUnassignedByEvent(EventDto event) {
        if(event == null) {
            return Set.of();
        }
        return userRepository.findAllUnassignedByEvent(event.getId()).stream()
                .map(u -> UserDto.builder()
                        .id((Integer) u.get("id"))
                        .username(getStringField(u.get("username")))
                        .build())
                .collect(Collectors.toSet());
    }

    public Optional<UserDto> findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .map(u -> mapWithoutParticipantAndPhoto(u)
                        .photo(u.getPhoto())
                        .participants(u.getParticipants().stream()
                                .map(p -> ParticipantDto.builder()
                                        .id(p.getId())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build());
    }

    private UserDto.UserDtoBuilder<?, ?> mapWithoutParticipantAndPhoto(User u) {
        return UserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .jobTitle(u.getJobTitle())
                .email(u.getEmail())
                .password(u.getPassword())
                .userRole(mapper.toDto(u.getUserRole()))
                .logged(u.getLogged());
    }

//    @Override
//    public UserDto save(UserDto user) {
//        if (user.getPassword() == null || user.getPassword().isEmpty()) {
//            userRepository.findById(user.getId())
//                    .map(User::getPassword)
//                    .ifPresent(user::setPassword);
//        } else {
//            user.setPassword(encodePassword(user.getPassword()));
//        }
//        if (user.getUserRole() == null) {
//            user.setUserRole(userRoleService.findDefault().orElse(null));
//        }
//        User entity = mapper.toEntity(user);
//        User savedEntity = userRepository.save(entity);
//        return mapper.toDto(savedEntity);
//    }

    public void updateLogged(UserDto user) {
        userRepository.updateLogged(user.getId());
    }

    @Override
    public UserDto save(UserDto user) {
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

    public void updatePassword(UserDto user, String password) {
        userRepository.updatePassword(user.getId(), password);
    }
}
