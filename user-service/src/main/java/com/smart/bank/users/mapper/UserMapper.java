package com.smart.bank.users.mapper;

import com.smart.bank.users.dto.UserRequestDTO;
import com.smart.bank.users.dto.UserResponseDTO;
import com.smart.bank.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", source = "id")
    UserResponseDTO toResponseDTO(User user);
}
