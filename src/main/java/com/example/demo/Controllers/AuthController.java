package com.example.demo.Controllers;

import com.example.demo.Classes.User;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Implementation.CustomerUser;
import com.example.demo.Implementation.UserImplementation;
import com.example.demo.Response.AuthResponse;
import com.example.demo.Response.Login;
import com.example.demo.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserImplementation userImplementation;
    @Autowired
    private UserAll userAll;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUser customerUser;
    @PostMapping("/signup")
    public AuthResponse add(@RequestBody User user) throws Exception {
        User newUser = new User();

        User check=userAll.searchByUserName(user.getUserName());
        if(check!=null){
            throw new Exception("User exist with username :" + user.getUserName());
        }
        newUser.setFollowers(user.getFollowers());
        newUser.setFollowing(user.getFollowing());
        newUser.setPosts(user.getPosts());
        newUser.setSaved(user.getSaved());
        newUser.setUserName(user.getUserName());
        newUser.setCommented(user.getCommented());
        newUser.setLiked(user.getLiked());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setGender(user.getGender());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Authentication authentication=new UsernamePasswordAuthenticationToken(newUser.getUserName(),newUser.getPassword());
        String token = JwtProvider.generateToken(authentication);

        userAll.save(newUser);

        return new AuthResponse(token,"Registered Successfully");
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody Login login) throws Exception {
        Authentication authentication=authenticate(login.getUsername(),login.getPassword());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token,"Login Successfully");

    }

    private Authentication authenticate(String username, String password)  {
        UserDetails userDetails=customerUser.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Password is incorrect");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
