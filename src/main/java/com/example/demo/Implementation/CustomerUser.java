package com.example.demo.Implementation;

import com.example.demo.Classes.User;
import com.example.demo.DataBase.UserAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUser implements UserDetailsService {
    @Autowired
    private UserAll userAll;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userAll.searchByUserName(username);
        if(user==null){

            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
    }
}
