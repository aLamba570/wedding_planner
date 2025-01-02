package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.VendorDTO;
import com.weddingplanner.wedding_planner.model.Vendor;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {ReviewMapper.class, PriceCategoryMapper.class})
public interface VendorMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Vendor toEntity(VendorDTO dto);

    VendorDTO toDTO(Vendor vendor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(VendorDTO dto, @MappingTarget Vendor vendor);

    // PriceRange mappings
    VendorDTO.PriceRangeDTO toPriceRangeDTO(Vendor.PriceRange priceRange);
    Vendor.PriceRange toPriceRange(VendorDTO.PriceRangeDTO dto);

    // Nested object mappings
    @Mapping(target = "closed", source = "closed")
    Vendor.BusinessHours.DaySchedule toDaySchedule(VendorDTO.BusinessHoursDTO.DayScheduleDTO dto);

    VendorDTO.BusinessHoursDTO.DayScheduleDTO toDayScheduleDto(Vendor.BusinessHours.DaySchedule entity);

    // Address mappings
    Vendor.Address toAddress(VendorDTO.AddressDTO dto);
    VendorDTO.AddressDTO toAddressDto(Vendor.Address address);

    // Contact Info mappings
    Vendor.ContactInfo toContactInfo(VendorDTO.ContactInfoDTO dto);
    VendorDTO.ContactInfoDTO toContactInfoDto(Vendor.ContactInfo contactInfo);

    // Social Media mappings
    Vendor.SocialMedia toSocialMedia(VendorDTO.SocialMediaDTO dto);
    VendorDTO.SocialMediaDTO toSocialMediaDto(Vendor.SocialMedia socialMedia);
}