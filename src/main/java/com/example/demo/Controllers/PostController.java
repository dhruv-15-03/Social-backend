package com.example.demo.Controllers;

import com.example.demo.Classes.Comments;
import com.example.demo.Classes.Post;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.PostAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Implementation.PostImplement;
import com.example.demo.Implementation.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    UserAll userAll;
    @Autowired
    PostAll postAll;
    @Autowired
    PostImplement postImplement;
    @Autowired
    UserImplementation userImplementation;
    @PostMapping("/new")
    public Post New(@RequestBody Post post,@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return postImplement.newPost(post, user.getId());
    }
    @GetMapping("/get/{postId}")
    public Post get(@PathVariable Integer postId){
        return postImplement.getPost(postId);
    }
    @GetMapping("/getAll")
    public List<Post> getAll(){

        return postAll.findAll();
    }

    @GetMapping("/user")
    public List<Post> usersPost(@RequestHeader ("Authorization") String jwt) {
        User user=userImplementation.getFromJwt(jwt);
        return postImplement.Users(user.getId());
    }

    @GetMapping("/reels")
    public List<Post> reels(){
        return postAll.reels();
    }
    @DeleteMapping("/delete/{postId}")
    public String delete(@PathVariable Integer postId,@RequestHeader ("Authorization") String jwt) throws Exception {
        User user=userImplementation.getFromJwt(jwt);
        return postImplement.DeletePost(postImplement.getPost(postId),user.getId());
    }
    @GetMapping("/like/{postId}")
    public Post Like(@PathVariable Integer postId,@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return postImplement.Like(postImplement.getPost(postId), user.getId());
    }
    @PostMapping("/comment/{postId}")
    public Post Comment(@PathVariable Integer postId,@RequestHeader ("Authorization") String jwt, @RequestBody Comments comment){
        User user=userImplementation.getFromJwt(jwt);
        return postImplement.comment(postImplement.getPost(postId), user.getId(), comment);
    }
    @GetMapping("/save/{postId}")
    public Post Save(@PathVariable Integer postId,@RequestHeader ("Authorization") String jwt){
        User user =userImplementation.getFromJwt(jwt);
        return postImplement.save(postImplement.getPost(postId), user.getId());
    }
    @GetMapping("/saved")
    public List<Post> saved(@RequestHeader ("Authorization") String jwt){
        User user =userImplementation.getFromJwt(jwt);

        return postImplement.Saved(user.getId());
    }
    @GetMapping("/commented")
    public List<Post> commented(@RequestHeader ("Authorization") String jwt){
        User user =userImplementation.getFromJwt(jwt);

        return postImplement.Commented(user.getId());
    }
    @GetMapping("/liked")
    public List<Post> liked(@RequestHeader ("Authorization") String jwt){
        User user =userImplementation.getFromJwt(jwt);
        return postImplement.Liked(user.getId());
    }
    @GetMapping("/get/{query}")
    public List<Post> getAll(@PathVariable String query){
        return postImplement.searchPost(query);
    }
    @GetMapping("/userReels/{userId}")
    public List<Post> userReels(@PathVariable Integer userId){
        return postAll.userReels(userId);
    }
}
