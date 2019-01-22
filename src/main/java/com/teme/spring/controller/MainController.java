package com.teme.spring.controller;

import com.teme.spring.DAO.AppUserDAO;
import com.teme.spring.entities.AppUser;
import com.teme.spring.entities.AppUserForm;
import com.teme.spring.validator.AppUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

import com.teme.spring.utils.WebUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    private final AppUserValidator validator;
    private final AppUserDAO appUserDAO;
    @Autowired
    public MainController(AppUserValidator validator, AppUserDAO appUserDAO) {
        this.validator = validator;
        this.appUserDAO = appUserDAO;
    }

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        Object target = webDataBinder.getTarget();
        if (target == null) {
            return;
        }
        if (target.getClass() == validator.getClass()) {
            webDataBinder.setValidator(validator);
        }
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model) {
        AppUserForm appUserForm = new AppUserForm();
        model.addAttribute("appUserForm", appUserForm);
        return "registerPage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm,
                           BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registerPage";
        } else {
            appUserDAO.saveAppUser(appUserForm);
        }
        return "loginPage";
    }
}
