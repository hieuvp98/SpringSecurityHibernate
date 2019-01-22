package com.teme.spring.entities;

import javax.persistence.*;

@Entity
@Table(name = "App_User")
public class AppUser {
    @Id
    @GeneratedValue
    private int userId;
    @Column(name = "USER_NAME", length = 36, nullable = false)
    private String userName;

    @Column(name = "ENCRYPTED_PASSWORD", length = 128, nullable = false)
    private String encryptedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private int phoneNumber;

    public AppUser() {

    }

    public AppUser(String userName, String encryptedPassword, boolean enabled, String firstName, String lastName, int phoneNumber) {
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
