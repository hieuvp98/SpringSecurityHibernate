package com.teme.spring.DAO;

import com.teme.spring.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class AppRoleDAO {
    private final EntityManager entityManager;

    @Autowired
    public AppRoleDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List getRoleNames(int userId) {
        String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " //
                + " where ur.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
