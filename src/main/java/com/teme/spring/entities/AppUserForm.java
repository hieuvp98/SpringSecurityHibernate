package com.teme.spring.entities;

public class AppUserForm {

    private String UserName;
    private String FirstName;
    private String lastName;
    private boolean enabled;
    private String password;
    private String confirmPassword;

    public AppUserForm(String userName, String firstName, String lastName, boolean enabled, String password, String confirmPassword) {
        UserName = userName;
        FirstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public AppUserForm() {

    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
