package ru.hh.school.converter;

import lombok.RequiredArgsConstructor;
import ru.hh.school.dto.SalaryDto;
import ru.hh.school.entity.Vacancy;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class SalaryConverter {

    public SalaryDto salaryToDto(Vacancy vacancy) {
        return SalaryDto
                .builder()
                .to(vacancy.getSalaryTo())
                .from(vacancy.getSalaryFrom())
                .currency(vacancy.getSalaryCurrency())
                .gross(vacancy.isSalaryGross())
                .build();
    }
}
