package ru.hh.school.converter;

import lombok.RequiredArgsConstructor;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.school.Popularity;
import ru.hh.school.dto.EmployerDto;
import ru.hh.school.dto.EmployerGetFavoritesDto;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;

import javax.inject.Singleton;
import java.time.LocalDateTime;

@Singleton
@RequiredArgsConstructor
public class EmployerConverter {
    private final FileSettings fileSettings;
    private final AreaConverter areaConverter;

    public EmployerGetFavoritesDto employerToFavoritesDto(Employer employer) {
        return EmployerGetFavoritesDto
                .builder()
                .id(employer.getId())
                .name(employer.getName())
                .dateCreate(employer.getDateCreate().toString())
                .description(employer.getDescription())
                .area(areaConverter.areaToDto(employer.getArea()))
                .comment(employer.getComment())
                .popularity(employer.getViewsCount() > fileSettings.getInteger("viewsToBePopular") ?
                        Popularity.POPULAR : Popularity.REGULAR)
                .viewsCount(employer.getViewsCount())
                .build();
    }

    public Employer dtoToEmployer(EmployerDto employerDto, Area area, String comment) {
        return Employer.builder()
                .id(employerDto.getId())
                .name(employerDto.getName())
                .dateCreate(LocalDateTime.now())
                .description(employerDto.getDescription())
                .area(area)
                .comment(comment)
                .build();
    }

}
