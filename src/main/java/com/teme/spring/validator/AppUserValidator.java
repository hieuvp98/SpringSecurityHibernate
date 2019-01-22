package com.teme.spring.validator;

import com.teme.spring.DAO.AppUserDAO;
import com.teme.spring.entities.AppUser;
import com.teme.spring.entities.AppUserForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class AppUserValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    private final AppUserDAO appUserDAO;

    @Autowired
    public AppUserValidator(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == AppUserForm.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        AppUserForm appUserForm = (AppUserForm) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty.appUserForm.phoneNumber");
        if (this.emailValidator.isValid(appUserForm.getUserName())) {
            errors.rejectValue("email", "Pattern.appUserForm.email");
        } else {
            AppUser user = appUserDAO.findUserAccount(appUserForm.getUserName());
            if (user != null)
                errors.rejectValue("email", "Duplicate.appUserForm.email");
        }
        if (!errors.hasErrors()) {
            if (!appUserForm.getConfirmPassword().equals(appUserForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.appUserForm.confirmPassword");
            }
        }
    }
}
