package com.abhinaba.scalemart.mapper;

import com.abhinaba.scalemart.dto.AddressDTO;
import com.abhinaba.scalemart.dto.UserDTO;
import com.abhinaba.scalemart.model.Address;
import com.abhinaba.scalemart.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "addresses", source = "addresses")
    Users toEntity(UserDTO userDTO);

    UserDTO toDTO(Users users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDTO(Address address);
}