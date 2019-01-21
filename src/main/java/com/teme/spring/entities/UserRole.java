package com.teme.spring.entities;

import javax.persistence.*;

@Entity
@Table(name = "User_Role")
public class UserRole {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id",nullable = false)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_Id", nullable = false)
    private AppRole appRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppRole getAppRole() {
        return appRole;
    }

    public void setAppRole(AppRole appRole) {
        this.appRole = appRole;
    }
}
