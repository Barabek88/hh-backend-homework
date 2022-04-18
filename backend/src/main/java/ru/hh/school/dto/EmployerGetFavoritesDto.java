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
public class EmployerGetFavoritesDto {
    private long id;
    private String name;
    @JsonProperty(value = "date_create")
    private String dateCreate;
    private String description;
    private AreaDto area;
    private String comment;
    private Popularity popularity;
    @JsonProperty(value = "views_count")
    private int viewsCount;
}
