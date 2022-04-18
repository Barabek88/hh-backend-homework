package ru.hh.school.converter;

import lombok.RequiredArgsConstructor;
import ru.hh.nab.common.properties.FileSettings;
import ru.hh.school.Popularity;
import ru.hh.school.dto.*;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;
import ru.hh.school.entity.Vacancy;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
@RequiredArgsConstructor
public class VacancyConverter {
    private final FileSettings fileSettings;
    private final AreaConverter areaConverter;
    private final EmployerConverter employerConverter;
    private final SalaryConverter salaryConverter;
    private final DateTimeFormatter strToDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    private final DateTimeFormatter dateToStrFormat = DateTimeFormatter.ISO_DATE_TIME;

    public VacancyGetFavoritesDto vacancyToFavoritesDto(Vacancy vacancy) {
        return VacancyGetFavoritesDto
                .builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .dateCreate(vacancy.getDateCreate().toString())
                .area(areaConverter.areaToDto(vacancy.getArea()))
                .comment(vacancy.getComment())
                .employer(employerConverter.employerToFavoritesDto(vacancy.getEmployer()))
                .salary(salaryConverter.salaryToDto(vacancy))
                .createdAt(vacancy.getCreatedAt().format(dateToStrFormat))
                .popularity(vacancy.getViewsCount() > fileSettings.getInteger("viewsToBePopular") ?
                        Popularity.POPULAR : Popularity.REGULAR)
                .viewsCount(vacancy.getViewsCount())
                .build();
    }

    public Vacancy dtoToVacancy(VacancyDto vacancyDto, Employer employer, Area areaVacancy, String comment) {
        return Vacancy.builder()
                .id(vacancyDto.getId())
                .name(vacancyDto.getName())
                .dateCreate(LocalDateTime.now())
                .area(areaVacancy)
                .salaryTo(vacancyDto.getSalary() == null ? null : vacancyDto.getSalary().getTo())
                .salaryFrom(vacancyDto.getSalary() == null ? null : vacancyDto.getSalary().getFrom())
                .salaryCurrency(vacancyDto.getSalary() == null ? null : vacancyDto.getSalary().getCurrency())
                .salaryGross(vacancyDto.getSalary() == null ? null : vacancyDto.getSalary().isGross())
                .createdAt(LocalDateTime.parse(vacancyDto.getCreatedAt(), strToDateFormat))
                .employer(employer)
                .viewsCount(0)
                .comment(comment)
                .build();
    }
}
