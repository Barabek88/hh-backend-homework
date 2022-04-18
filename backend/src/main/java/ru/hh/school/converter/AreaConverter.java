package ru.hh.school.converter;

import lombok.RequiredArgsConstructor;
import ru.hh.school.dto.AreaDto;
import ru.hh.school.entity.Area;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class AreaConverter {

    public AreaDto areaToDto(Area area) {
        return AreaDto
                .builder()
                .id(area.getId())
                .name(area.getName())
                .build();
    }
}
