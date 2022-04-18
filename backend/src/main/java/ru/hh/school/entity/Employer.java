package ru.hh.school.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employer")
@Builder
public class Employer {
    @Id
    @Column(name = "employer_id")
    private long id;
    private String name;
    @Column(name = "date_create")
    private LocalDateTime dateCreate;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "area_id")
    private Area area;
    private String comment;
    @Column(name = "views_count")
    private int viewsCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
