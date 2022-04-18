package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyAddFavoritesDto {
    @JsonProperty("vacancy_id")
    private long vacancyId;
    private String comment;
}
