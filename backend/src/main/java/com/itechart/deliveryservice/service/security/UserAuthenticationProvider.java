package com.itechart.deliveryservice.service.security;

import com.itechart.deliveryservice.dao.UserDao;
import com.itechart.deliveryservice.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationProvider implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        com.itechart.deliveryservice.entity.User user
                = userDao.getByName(username);
        User ud = new User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user.getRole())
        );
        return ud;
    }

    private List<SimpleGrantedAuthority> getAuthorities(UserRole role) {

        List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_"+role.toString()));
        return authList;
    }
}
