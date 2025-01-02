package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.AddressDTO;
import com.weddingplanner.wedding_planner.dto.SocialMediaDTO;
import com.weddingplanner.wedding_planner.model.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommonMapper {
    AddressDTO addressToDto(Vendor.Address address);
    Vendor.Address dtoToAddress(AddressDTO dto);

    SocialMediaDTO socialMediaToDto(Vendor.SocialMedia socialMedia);
    Vendor.SocialMedia dtoToSocialMedia(SocialMediaDTO dto);
}
