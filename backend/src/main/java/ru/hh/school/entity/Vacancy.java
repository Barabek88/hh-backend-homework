package ru.hh.school.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @Column(name = "vacancy_id")
    private long id;
    private String name;
    @Column(name = "date_create")
    private LocalDateTime dateCreate;
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "area_id")
    private Area area;
    @Column(name = "salary_to")
    private Integer salaryTo;
    @Column(name = "salary_from")
    private Integer salaryFrom;
    @Column(name = "salary_currency")
    private String salaryCurrency;
    @Column(name = "salary_gross")
    private boolean salaryGross;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "employer_id")
    private Employer employer;
    private String comment;
    @Column(name = "views_count")
    private int viewsCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id);
    }

    @Override
    public int hashCode() {
        return 17;
    }
}
