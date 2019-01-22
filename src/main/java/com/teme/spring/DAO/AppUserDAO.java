package com.teme.spring.DAO;

import com.teme.spring.entities.AppUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class AppUserDAO {

    private final EntityManager entityManager;
    private final SessionFactory sessionFactory;

    @Autowired
    public AppUserDAO(EntityManager entityManager, SessionFactory sessionFactory) {
        this.entityManager = entityManager;
        this.sessionFactory = sessionFactory;
    }

    public AppUser findUserAccount(String userName) {
        try {
            String sql = "Select e from " + AppUser.class.getName() + " e " //
                    + " Where e.userName = :userName ";

            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("userName", userName);

            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void saveAppUser(AppUser appUser) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(appUser);
    }

    public List<AppUser> findAll() {
        try {
            String hql = "from AppUser";
            return entityManager.createQuery(hql, AppUser.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
