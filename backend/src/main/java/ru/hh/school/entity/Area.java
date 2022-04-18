package ru.hh.school.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "area")
public class Area {
    @Id
    @Column(name = "area_id")
    private long id;
    private String name;
}
