package com.teme.spring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teme.spring.entities.GooglePojo;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleService {

    @Value("${spring.social.google.appId}")
    private String appId;
    @Value("${spring.social.google.appSecret}")
    private String appSecret;
    private String redirect = "http://localhost:8866/google";
    @Value("${google.link.get.user_info}")
    private String linkUser;
    private String accessToken;

    public String createGoogleAuthorizationURL(){
        GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(appId,appSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(redirect);
        params.setScope("email");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createGoogleAccessToken(String code){
        GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(appId,appSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code,redirect,null);
        accessToken = accessGrant.getAccessToken();
    }

    public String getEmail(){
        Google google = new GoogleTemplate(accessToken);
        String[] fields = {"id", "name","birthday","email","gender","location"};
        return google.plusOperations().getGoogleProfile().getAccountEmail();
    }
    public GooglePojo getGooglePojo() {
        String link = linkUser+accessToken;
        String response = null;
        GooglePojo pojo = null;
        try {
            response = Request.Get(link).execute().returnContent().asString();
           ObjectMapper mapper = new ObjectMapper();
            pojo = mapper.readValue(response, GooglePojo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
