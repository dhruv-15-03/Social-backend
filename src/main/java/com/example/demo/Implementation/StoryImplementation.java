package com.example.demo.Implementation;

import com.example.demo.Classes.Story;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.StoryAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.StoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StoryImplementation implements StoryMethods{
    @Autowired
    private StoryAll storyAll;
    @Autowired
    private UserAll userAll;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public Story post(Story story, User user) {
        story.setTime(LocalDateTime.now());
        scheduleExpiration(story);
        story.setMain(user);

        List<Story> temp = user.getStory();
        temp.add(story);
        user.setStory(temp);

        userAll.save(user);
        storyAll.save(story);

        return story;
    }

    private void scheduleExpiration(Story story) {
        scheduler.schedule(() -> {
            try {
                expire(story);
            } catch (StoryException e) {
                // Handle exception (log the error)
                System.err.println("Error expiring story: " + e.getMessage());
            }
        }, 24, TimeUnit.HOURS);
    }

    private void expire(Story story) throws StoryException {
        Delete(story.getId(), story.getMain());
    }
    @Override
    public Story like(Integer storyId, User user) throws StoryException {
        Story story=storyAll.getReferenceById(storyId);
        User acc=story.getMain();
        if(!user.getFollowing().contains(acc))
            throw new StoryException("Not Following can't like the Story");
        List<User> like=story.getLiked();
        if(like.contains(user))
            like.remove(user);
        else
            like.add(user);
        story.setLiked(like);
        storyAll.save(story);
        return story;
    }

    @Override
    public String Delete(Integer storyId, User user) throws StoryException {
        Story story=storyAll.getReferenceById(storyId);
        if(!Objects.equals(user.getId(), story.getMain().getId()))
            throw new StoryException("Not Your Story");
        storyAll.delete(story);
        List<Story> store=user.getStory();
        store.remove(story);
        user.setStory(store);
        userAll.save(user);
        return "Deleted Successfully";
    }

    @Override
    public Story view(Integer storyId, User user) throws StoryException {
        Story story=storyAll.getReferenceById(storyId);
        User acc=story.getMain();
        if(!user.getFollowing().contains(acc))
            throw new StoryException("Not Following can't view the Story");
        List<User> view=story.getViews();
        if(view.contains(user))
            view.remove(user);
        else
            view.add(user);
        story.setViews(view);
        storyAll.save(story);
        return story;
    }

    @Override
    public List<Story> getAll() {
        return storyAll.findAll();
    }

    @Override
    public List<Story> getUsers(User user) {
        List<User> following=user.getFollowing();
        List<Story> stories=new ArrayList<>();
        for(User user1 : following){
            if(user1.getStory()!=null){
                stories.addAll(user1.getStory());
            }
        }
        return stories;
    }

    @Override
    public List<Story> getSelf(User user) {
        return user.getStory();
    }

    @Override
    public List<User> users(User user) {
        List<User> users=user.getFollowing();
        users.removeIf(user1 -> user1.getStory().isEmpty());
        return users;
    }
}
