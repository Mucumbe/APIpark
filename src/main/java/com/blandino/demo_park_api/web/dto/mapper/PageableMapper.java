package com.blandino.demo_park_api.web.dto.mapper;

import com.blandino.demo_park_api.web.dto.PageableDto;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor
public class PageableMapper {

    public static PageableDto toDto(Page page){
        return new ModelMapper().map(page,PageableDto.class);
    }
}
