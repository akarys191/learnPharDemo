package com.pharm.demo.web.security;

import com.pharm.demo.model.PharmUser;
import com.pharm.demo.services.PharmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SecurityAppUserDetailsServiceImpl implements UserDetailsService {

    PharmUserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityAppUserDetailsServiceImpl(PharmUserService userService,
                                                PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        PharmUser user = userService.findByUserName(userName);
        if(user == null){
            throw new UsernameNotFoundException("Username not found"+userName);
        }
        List<GrantedAuthority> authorities = Stream.of(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserName(),passwordEncoder.encode(user.getPassword()), true, true, true,
                true,authorities);
    }
}