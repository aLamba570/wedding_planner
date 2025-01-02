package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.ReviewDTO;
import com.weddingplanner.wedding_planner.model.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDTO toDTO(Review review);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review toEntity(ReviewDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ReviewDTO dto, @MappingTarget Review review);
}