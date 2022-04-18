package ru.hh.school.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import ru.hh.school.entity.Employer;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class EmployerDao {
    private final SessionFactory sessionFactory;

    public Employer getEmployer(long employerId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employer e where e.id = :id", Employer.class)
                .setParameter("id", employerId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null)
                ;
    }

    public void save(Employer employer) {
        sessionFactory.getCurrentSession().save(employer);
    }

    public List<Employer> getEmployers(int page, int perPage) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employer e " +
                        "left join fetch e.area", Employer.class)
                .setFirstResult(page * perPage)
                .setMaxResults(perPage)
                .getResultList();
    }

    public void delete(Employer employer) {
        sessionFactory.getCurrentSession().delete(employer);
    }
}