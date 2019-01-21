package com.teme.spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {

    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    private String facebookAppSecret;

    private String accessToken;

    public String createFacebookAuthorizationURL(){
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookAppSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://localhost:8866/facebook");
        params.setScope("public_profile,email,gender,birthday");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createFacebookAccessToken(String code){
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookAppSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8866/facebook", null);
        accessToken =  accessGrant.getAccessToken();
    }

    public User getUser() {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "name","birthday","email","gender","location"};
        return facebook.fetchObject("me",User.class,fields);
    }
}
