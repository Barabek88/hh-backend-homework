package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyDto {
    private long id;
    private String name;
    private AreaDto area;
    private SalaryDto salary;
    @JsonProperty("created_at")
    private String createdAt;
    private EmployerNameDto employer;
}
