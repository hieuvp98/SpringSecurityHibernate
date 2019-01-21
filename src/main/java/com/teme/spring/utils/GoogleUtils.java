package com.teme.spring.utils;

import com.teme.spring.entities.GooglePojo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleUtils {


    public UserDetails buildUser(GooglePojo googlePojo){
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(googlePojo.getEmail(),"",enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
        return userDetails;
    }
}
