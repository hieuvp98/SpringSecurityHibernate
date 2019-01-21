package com.teme.spring.controller;

import com.teme.spring.entities.GooglePojo;
import com.teme.spring.services.FacebookService;
import com.teme.spring.services.GoogleService;
import com.teme.spring.utils.FacebookUtils;
import com.teme.spring.utils.GoogleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SocialLoginController {

    private final
    FacebookService facebookService;
    private final
    GoogleService googleService;
    private final
    GoogleUtils googleUtils;
    private final FacebookUtils facebookUtils;
    @Autowired
    public SocialLoginController(FacebookService facebookService, GoogleService googleService, GoogleUtils googleUtils,FacebookUtils facebookUtils) {
        this.facebookService = facebookService;
        this.googleUtils = googleUtils;
        this.googleService = googleService;
        this.facebookUtils = facebookUtils;
    }

    @RequestMapping("/login-facebook")
    public String loginFacebook() {
        String url = facebookService.createFacebookAuthorizationURL();
        return "redirect:" + url;
    }

    @RequestMapping("/login-google")
    public String loginGoogle() {
        String url = googleService.createGoogleAuthorizationURL();
        return "redirect:" + url;
    }

    @RequestMapping("/google")
    public String google(Model model, @RequestParam("code") String code, HttpServletRequest request) {
        googleService.createGoogleAccessToken(code);
        GooglePojo googlePojo = googleService.getGooglePojo();
        UserDetails user = googleUtils.buildUser(googlePojo);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return "redirect:/userInfo";
    }
    @RequestMapping("/facebook")
    public String facebook(Model model,@RequestParam("code") String code, HttpServletRequest httpServletRequest){
        facebookService.createFacebookAccessToken(code);
        UserDetails userDetails = facebookUtils.buildUser(facebookService.getUser());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return "redirect:/userInfo";
    }
}
