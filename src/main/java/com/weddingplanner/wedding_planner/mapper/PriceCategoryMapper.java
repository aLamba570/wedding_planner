package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.VendorDTO;
import com.weddingplanner.wedding_planner.model.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Mapper(componentModel = "spring")
public interface PriceCategoryMapper {

    @ValueMappings({
            @ValueMapping(source = "BUDGET", target = "BUDGET"),
            @ValueMapping(source = "MODERATE", target = "MODERATE"),
            @ValueMapping(source = "PREMIUM", target = "PREMIUM"),
            @ValueMapping(source = "LUXURY", target = "LUXURY")
    })
    VendorDTO.PriceRangeDTO.PriceCategoryDTO toDTO(Vendor.PriceRange.PriceCategory category);

    @ValueMappings({
            @ValueMapping(source = "BUDGET", target = "BUDGET"),
            @ValueMapping(source = "MODERATE", target = "MODERATE"),
            @ValueMapping(source = "PREMIUM", target = "PREMIUM"),
            @ValueMapping(source = "LUXURY", target = "LUXURY")
    })
    Vendor.PriceRange.PriceCategory toEntity(VendorDTO.PriceRangeDTO.PriceCategoryDTO category);
}