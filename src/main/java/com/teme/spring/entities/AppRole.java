package com.teme.spring.entities;

import javax.persistence.*;

@Entity
@Table(name = "App_Role")
public class AppRole {
    @Id
    @GeneratedValue
    private int roleId;
    @Column(name = "Role_Name", length = 30, nullable = false)
    private String roleName;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
