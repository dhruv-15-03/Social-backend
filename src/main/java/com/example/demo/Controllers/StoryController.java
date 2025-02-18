package com.example.demo.Controllers;

import com.example.demo.Classes.Story;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.StoryAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.StoryException;
import com.example.demo.Implementation.StoryImplementation;
import com.example.demo.Implementation.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {
    @Autowired
    private UserAll userAll;
    @Autowired
    private StoryAll storyAll;
    @Autowired
    private UserImplementation userImplementation;
    @Autowired
    private StoryImplementation storyImplementation;
    @PostMapping("/new")
    public Story post(@RequestBody Story story, @RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.post(story,user);
    }
    @GetMapping("/like/{storyId}")
    public Story like(@PathVariable Integer storyId,@RequestHeader ("Authorization") String jwt) throws StoryException {
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.like(storyId,user);
    }
    @GetMapping("/delete/{storyId}")
    public String delete(@PathVariable Integer storyId,@RequestHeader ("Authorization") String jwt) throws Exception {
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.Delete(storyId,user);
    }
    @GetMapping("/view/{storyId}")
    public Story view(@PathVariable Integer storyId,@RequestHeader ("Authorization") String jwt) throws Exception {
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.view(storyId,user);
    }
    @GetMapping("/self")
    public List<Story> self(@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.getSelf(user);
    }
    @GetMapping("/users")
    public List<User> users(@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.users(user);
    }
    @GetMapping("/getUser/{userId}")
    public List<Story> userStory(@PathVariable Integer userId){
        User user =userImplementation.getFromId(userId);
        return storyImplementation.getSelf(user);
    }
    @GetMapping("/getUsers")
    public List<Story> getUsers(@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return storyImplementation.getUsers(user);
    }
}
