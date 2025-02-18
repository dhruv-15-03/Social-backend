package com.example.demo.Implementation;

import com.example.demo.Classes.Post;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.UserException;
import com.example.demo.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserImplementation {
    @Autowired
    UserAll userAll;
    public User getFromId(Integer id){
        Optional<User> user= Optional.of((userAll.getReferenceById(id)));
        return user.get();
    }
    public String deleteFromId(Integer id,User user) throws UserException {
        if(!Objects.equals(user.getId(), id)){
            throw new UserException("You are not the user can't delete");
        }
        userAll.deleteById(id);
        return "User deleted with userid "+id ;
    }
    public User getFromJwt(String jwt){
        String userName= JwtProvider.getUserNameFromJwt(jwt);
        return userAll.searchByUserName(userName);
    }
    public UserAll getuSerAll() {
        return userAll;
    }
    public List<Post> getPost(Integer userId ){
        User user=userAll.getReferenceById(userId);
        return user.getPosts();
    }
    public User follow(Integer userId1,Integer userId2){
        User user=userAll.getReferenceById(userId1);
        User user2=userAll.getReferenceById(userId2);
        List<User> u1=user.getFollowing();
        List<User> u2=user2.getFollowers();
        if(u1.contains(user2)){
            u1.remove(user2);
            u2.remove(user);
        }
        else{
            u1.add(user2);
            u2.add(user);
        }
        user.setFollowing(u1);
        user2.setFollowers(u2);
        userAll.save(user);
        userAll.save(user2);
        return user;
    }
    public User Update(User user,User user2) throws UserException {
        User old=userAll.getReferenceById(user.getId());
        if(user2.getName()!=null){
            old.setName(user2.getName());
        }
        if(user2.getEmail()!=null){
            old.setEmail(user2.getEmail());
        }
        if(user2.getUserName()!=null){
            if(userAll.searchByUserName(user2.getUserName())!=null&&!user2.getUserName().equals(old.getName())){
                throw new UserException("User already exist with this Username"+user2.getUserName());
            }
            old.setUserName(user2.getUserName());
        }
        if(user2.getBio()!=null)
            old.setBio(user2.getBio());
        if(user2.getProfile()!=null)
            old.setProfile(user2.getProfile());
        if(!user2.getSaved().isEmpty())
            old.setSaved(user2.getSaved());
        if(!user2.getCommented().isEmpty())
            old.setCommented(user2.getCommented());
        if (!user2.getLiked().isEmpty())
            old.setLiked(user2.getLiked());
        if (!user2.getFollowers().isEmpty())
            old.setFollowers(user2.getFollowers());
        if(!user2.getFollowing().isEmpty())
            old.setFollowing(user2.getFollowing());
        if(!user2.getPosts().isEmpty())
            old.setPosts(user2.getPosts());
        userAll.save(old);
        return old;
    }
    public List<User> searchUser(String query){
        return userAll.searchUser(query);
    }

}
