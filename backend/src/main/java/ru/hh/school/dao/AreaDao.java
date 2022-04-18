package ru.hh.school.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import ru.hh.school.entity.Area;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class AreaDao {
    private final SessionFactory sessionFactory;

    public Area getArea(long areaId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Area a where a.id =:id", Area.class)
                .setParameter("id", areaId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null)
                ;
    }

}
