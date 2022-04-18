package ru.hh.school.service;


import lombok.RequiredArgsConstructor;
import ru.hh.school.converter.EmployerConverter;
import ru.hh.school.converter.VacancyConverter;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.EmployerDao;
import ru.hh.school.dao.VacancyDao;
import ru.hh.school.dto.*;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;
import ru.hh.school.entity.Vacancy;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Transactional
@RequiredArgsConstructor
public class VacancyFavoritesService {
    private final HHDataService hhDataService;
    private final EmployerDao employerDao;
    private final AreaDao areaDao;
    private final VacancyDao vacancyDao;
    private final EmployerConverter employerConverter;
    private final VacancyConverter vacancyConverter;

    public long addVacancy(VacancyAddFavoritesDto vacancyAddFavoritesDto) {
        Vacancy vacancy = vacancyDao.getVacancy(vacancyAddFavoritesDto.getVacancyId());

        if (vacancy != null) {
            throw new IllegalArgumentException("Vacancy has been already added");
        }

        vacancy = getVacancyFromHH(vacancyAddFavoritesDto.getVacancyId(), vacancyAddFavoritesDto.getComment());
        vacancyDao.save(vacancy);

        return vacancy.getId();
    }


    private Vacancy getVacancyFromHH(long vacancyId, String comment) {
        VacancyDto vacancyDto = hhDataService.getVacancy(vacancyId);

        Area areaVacancy = areaDao.getArea(vacancyDto.getArea().getId());

        if (areaVacancy == null) {
            areaVacancy = new Area(vacancyDto.getArea().getId(), vacancyDto.getArea().getName());
        }

        Employer employer = employerDao.getEmployer(vacancyDto.getEmployer().getId());

        if (employer == null) {
            EmployerDto employerDto = hhDataService.getEmployer(vacancyDto.getEmployer().getId());

            Area employerArea = areaDao.getArea(employerDto.getArea().getId());

            if (employerArea == null) {
                employerArea = (employerDto.getArea().getId() == areaVacancy.getId())
                        ? areaVacancy
                        : new Area(employerDto.getArea().getId(), employerDto.getArea().getName());
            }

            employer = employerConverter.dtoToEmployer(employerDto, employerArea, "");
        }

        return vacancyConverter.dtoToVacancy(vacancyDto, employer, areaVacancy, comment);
    }

    public List<VacancyGetFavoritesDto> getVacanciesFromFavorites(int page, int perPage) {
        List<Vacancy> vacancies = vacancyDao.getVacancies(page, perPage);

        vacancies.forEach(vacancy -> {
            vacancy.setViewsCount(vacancy.getViewsCount() + 1);
            vacancy.getEmployer().setViewsCount(vacancy.getEmployer().getViewsCount() + 1);
            vacancyDao.save(vacancy);
        });

        return vacancies.stream()
                .map(vacancyConverter::vacancyToFavoritesDto)
                .collect(Collectors.toList());
    }

    public void deleteVacancyFromFavorites(long vacancyId) {
        Vacancy vacancy = vacancyDao.getVacancy(vacancyId);

        if (vacancy == null) {
            throw new IllegalArgumentException("Vacancy_id is absent");
        }

        vacancyDao.delete(vacancy);
    }

    public void refreshVacancyToFavorites(long vacancyId) {
        Vacancy vacancy = vacancyDao.getVacancy(vacancyId);

        if (vacancy == null) {
            throw new IllegalArgumentException("Vacancy_id is absent");
        }

       Vacancy vacancyFromHH = getVacancyFromHH(vacancyId, "");

        vacancy.setName(vacancyFromHH.getName());
        vacancy.setArea(vacancyFromHH.getArea());
        vacancy.setEmployer(vacancyFromHH.getEmployer());
        vacancy.setSalaryFrom(vacancyFromHH.getSalaryFrom());
        vacancy.setSalaryTo(vacancyFromHH.getSalaryTo());
        vacancy.setSalaryCurrency(vacancyFromHH.getSalaryCurrency());
        vacancy.setSalaryGross(vacancyFromHH.isSalaryGross());
    }
}
