package com.teme.spring.services;

import com.teme.spring.DAO.AppRoleDAO;
import com.teme.spring.DAO.AppUserDAO;
import com.teme.spring.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDAO appUserDAO;
    private final AppRoleDAO appRoleDAO;

    @Autowired
    public UserDetailsServiceImpl(AppUserDAO appUserDAO, AppRoleDAO appRoleDAO) {
        this.appUserDAO = appUserDAO;
        this.appRoleDAO = appRoleDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.appUserDAO.findUserAccount(userName);
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        return (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncryptedPassword(), grantList);
    }
}
