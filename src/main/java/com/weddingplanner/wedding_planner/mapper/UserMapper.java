package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.UserDTO;
import com.weddingplanner.wedding_planner.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {GuestMapper.class, ReviewMapper.class})
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", source = "role")  // Explicitly map role
    UserDTO toDTO(User user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", source = "role")  // Explicitly map role
    User toEntity(UserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UserDTO dto, @MappingTarget User user);
}