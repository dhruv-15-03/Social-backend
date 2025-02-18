package com.example.demo.Controllers;

import com.example.demo.Classes.Post;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.UserException;
import com.example.demo.Implementation.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserAll userAll;
    @Autowired
    private UserImplementation userImplementation;
    @GetMapping("/userAll")
    public List<User> getALl(@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        List<User> temp=userImplementation.getuSerAll().findAll();
        temp.remove(user);
        return temp;
    }
    @GetMapping("/{query}")
    public List<User> searchUser(@PathVariable ("query") String query){
        return userImplementation.searchUser(query);
    }

    @GetMapping("/follow/{userId2}")
    public User follow(@RequestHeader ("Authorization") String jwt,@PathVariable Integer userId2){
        User user=userImplementation.getFromJwt(jwt);
        return userImplementation.follow(user.getId(),userId2);
    }
    @GetMapping("/getUser/{userId}")
    public User getFromId(@PathVariable Integer userId){
        return userImplementation.getFromId(userId);
    }
    @PutMapping("/update")
    public User update(@RequestHeader ("Authorization") String jwt,@RequestBody User user) throws UserException {
        User main=userImplementation.getFromJwt(jwt);
        return userImplementation.Update(main,user);
    }
    @GetMapping("/getFollowing/{userId}")
    public List<User> getF(@PathVariable Integer userId){
        User user=userImplementation.getFromId(userId);
        return user.getFollowing();
    }
    @GetMapping("/getFollowers/{userId}")
    public List<User> getFwrs(@PathVariable Integer userId){
        User user=userImplementation.getFromId(userId);
        return user.getFollowers();
    }
    @GetMapping("/delete/{userId}")
    public String delete(@RequestHeader ("Authorization") String jwt,@PathVariable Integer userId) throws UserException {
        User user=userImplementation.getFromJwt(jwt);
        return userImplementation.deleteFromId(userId,user);
    }
    @PutMapping("/profile")
    public User profile(@RequestHeader ("Authorization") String jwt,@RequestBody User user) throws UserException {
        User main=userImplementation.getFromJwt(jwt);
        return userImplementation.Update(main,user);
    }
    @GetMapping("/postAll/{userId}")
    public List<Post> getPost(@RequestHeader ("Authorization") String jwt,@PathVariable Integer userId){
        return userImplementation.getPost(userId);
    }
    @GetMapping("/profile")
    public User getUserFromToken(@RequestHeader ("Authorization") String jwt){
        return userImplementation.getFromJwt(jwt);
    }
}
