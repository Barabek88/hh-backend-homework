package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.hh.school.Popularity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyGetFavoritesDto {
    private long id;
    private String name;
    @JsonProperty(value = "date_create")
    private String dateCreate;
    private AreaDto area;
    private String comment;
    private EmployerGetFavoritesDto employer;
    private SalaryDto salary;
    @JsonProperty("created_at")
    private String createdAt;
    private Popularity popularity;
    @JsonProperty(value = "views_count")
    private int viewsCount;
}
