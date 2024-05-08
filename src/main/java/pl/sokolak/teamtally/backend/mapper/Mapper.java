package pl.sokolak.teamtally.backend.mapper;

import org.mapstruct.Context;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.Code;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.Participant;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.suggestion.Suggestion;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDto;
import pl.sokolak.teamtally.backend.tag.Tag;
import pl.sokolak.teamtally.backend.tag.TagDto;
import pl.sokolak.teamtally.backend.team.Team;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.role.UserRole;
import pl.sokolak.teamtally.backend.user.role.UserRoleDto;

@org.mapstruct.Mapper
public interface Mapper {

    @DoIgnore
    default UserDto toDto(User entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    UserDto toDto(User entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default EventDto toDto(Event entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    EventDto toDto(Event entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default TeamDto toDto(Team entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    TeamDto toDto(Team entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ParticipantDto toDto(Participant entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    ParticipantDto toDto(Participant entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ChallengeDto toDto(Challenge entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    ChallengeDto toDto(Challenge entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default CodeDto toDto(Code entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    CodeDto toDto(Code entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default TagDto toDto(Tag entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    TagDto toDto(Tag entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default UserRoleDto toDto(UserRole entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    UserRoleDto toDto(UserRole entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default SuggestionDto toDto(Suggestion entity) {
        return toDto(entity, new CycleAvoidingMappingContext());
    }
    SuggestionDto toDto(Suggestion entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);



    @DoIgnore
    default User toEntity(UserDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    User toEntity(UserDto entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Event toEntity(EventDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Event toEntity(EventDto entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Team toEntity(TeamDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Team toEntity(TeamDto entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Participant toEntity(ParticipantDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Participant toEntity(ParticipantDto entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Challenge toEntity(ChallengeDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Challenge toEntity(ChallengeDto dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Code toEntity(CodeDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Code toEntity(CodeDto dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Tag toEntity(TagDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Tag toEntity(TagDto dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default UserRole toEntity(UserRoleDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    UserRole toEntity(UserRoleDto dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default Suggestion toEntity(SuggestionDto dto) {
        return toEntity(dto, new CycleAvoidingMappingContext());
    }
    Suggestion toEntity(SuggestionDto dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
