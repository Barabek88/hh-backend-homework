package ru.hh.school.service;


import lombok.RequiredArgsConstructor;
import ru.hh.school.converter.EmployerConverter;
import ru.hh.school.dao.AreaDao;
import ru.hh.school.dao.EmployerDao;
import ru.hh.school.dto.EmployerAddFavoritesDto;
import ru.hh.school.dto.EmployerDto;
import ru.hh.school.dto.EmployerGetFavoritesDto;
import ru.hh.school.dto.EmployerUpdateFavoritesDto;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Transactional
@RequiredArgsConstructor
public class EmployerFavoritesService {
    private final HHDataService hhDataService;
    private final EmployerDao employerDao;
    private final AreaDao areaDao;
    private final EmployerConverter employerConverter;

    public long addEmployer(EmployerAddFavoritesDto employerAddFavoritesDto) {
        Employer employer = employerDao.getEmployer(employerAddFavoritesDto.getEmployerId());

        if (employer != null) {
            throw new IllegalArgumentException("Employer has been already added");
        }

        EmployerDto employerDto = hhDataService.getEmployer(employerAddFavoritesDto.getEmployerId());

        Area area = areaDao.getArea(employerDto.getArea().getId());

        if (area == null) {
            area = new Area(employerDto.getArea().getId(), employerDto.getArea().getName());
        }

        employer = employerConverter.dtoToEmployer(employerDto, area, employerAddFavoritesDto.getComment());
        employerDao.save(employer);

        return employer.getId();
    }

    public List<EmployerGetFavoritesDto> getEmployersFromFavorites(int page, int perPage) {
        List<Employer> employers = employerDao.getEmployers(page, perPage);
        employers.forEach(e -> {
            e.setViewsCount(e.getViewsCount() + 1);
            employerDao.save(e);
        });

        return employers.stream()
                .map(employerConverter::employerToFavoritesDto)
                .collect(Collectors.toList());
    }

    public void updateEmployersFromFavorites(long employerId, EmployerUpdateFavoritesDto employerUpdateFavoritesDto) {
        Employer employer = employerDao.getEmployer(employerId);

        if (employer == null) {
            throw new IllegalArgumentException("Employer_id is absent");
        }

        employer.setComment(employerUpdateFavoritesDto.getComment());
        employerDao.save(employer);
    }

    public void deleteEmployersFromFavorites(long employerId) {
        Employer employer = employerDao.getEmployer(employerId);

        if (employer == null) {
            throw new IllegalArgumentException("Employer_id is absent");
        }

        employerDao.delete(employer);
    }

    public void refreshEmployerToFavorites(long employerId) {
        Employer employer = employerDao.getEmployer(employerId);

        if (employer == null) {
            throw new IllegalArgumentException("Employer_id is absent");
        }

        EmployerDto employerDto = hhDataService.getEmployer(employerId);
        Area area = areaDao.getArea(employerDto.getArea().getId());

        if (area == null) {
            area = new Area(employerDto.getArea().getId(), employerDto.getArea().getName());
        }

        employer.setName(employerDto.getName());
        employer.setDescription(employerDto.getDescription());
        employer.setArea(area);
    }
}
