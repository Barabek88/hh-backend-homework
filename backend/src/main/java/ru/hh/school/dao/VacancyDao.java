package ru.hh.school.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import ru.hh.school.entity.Vacancy;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class VacancyDao {
    private final SessionFactory sessionFactory;

    public Vacancy getVacancy(long vacancyId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Vacancy e where e.id = :id", Vacancy.class)
                .setParameter("id", vacancyId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null)
                ;
    }

    public void save(Vacancy vacancy) {
        sessionFactory.getCurrentSession().save(vacancy);
    }

    public List<Vacancy> getVacancies(int page, int perPage) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Vacancy e " +
                        "left join fetch e.area " +
                        "left join fetch e.employer", Vacancy.class)
                .setFirstResult(page * perPage)
                .setMaxResults(perPage)
                .getResultList();
    }

    public void delete(Vacancy vacancy) {
        sessionFactory.getCurrentSession().delete(vacancy);
    }

}