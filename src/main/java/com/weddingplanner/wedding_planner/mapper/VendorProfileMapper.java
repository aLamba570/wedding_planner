package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.VendorProfileDTO;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VendorProfileMapper {

    VendorProfileDTO toDTO(VendorProfile vendorProfile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VendorProfile toEntity(VendorProfileDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(VendorProfileDTO dto, @MappingTarget VendorProfile vendorProfile);
}