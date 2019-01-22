package com.teme.spring.DAO;

import com.teme.spring.entities.AppUser;
import com.teme.spring.entities.AppUserForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AppUserDAO(EntityManager entityManager, SessionFactory sessionFactory, BCryptPasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.sessionFactory = sessionFactory;
        this.passwordEncoder = passwordEncoder;
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

    public void saveAppUser(AppUserForm appUserForm) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(new AppUser(appUserForm.getUserName(), passwordEncoder.encode(appUserForm.getPassword()), false, appUserForm.getFirstName(), appUserForm.getLastName(), appUserForm.getPhoneNumber()));
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
