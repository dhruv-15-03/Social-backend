package com.example.demo.Implementation;

import com.example.demo.Classes.Comments;
import com.example.demo.Classes.Post;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.CommentsAll;
import com.example.demo.DataBase.PostAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.PostException;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostImplement implements PostMethods {
    @Autowired
    UserAll userAll;
    @Autowired
    PostAll postAll;
    @Autowired
    CommentsAll commentsAll;
    private final MeterRegistry meterRegistry;
    public PostImplement(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    @Override
    public Post newPost(Post post, Integer userId) {
       User user=userAll.getReferenceById(userId);
       meterRegistry.counter("thoughts.posts.created").increment();
       List<Post> temp=user.getPosts();
       temp.add(post);
       user.setPosts(temp);
       post.setUser(user);
       postAll.save(post);

       userAll.save(user);
       return post;
    }

    @Override
    public Post getPost(Integer postId) {
        return postAll.getReferenceById(postId);
    }

    @Override
    public String DeletePost(Post post, Integer userId) throws PostException {
        User user=userAll.getReferenceById(userId);
        List<Post> temp=user.getPosts();
        List<Post> l=user.getLiked();
        List<Post> s=user.getSaved();
        List<Post> c=user.getCommented();
        if(!temp.contains(post)){
            throw new PostException("User not contain that Post");
        }
        else{
            temp.remove(post);
            l.remove(post);
            s.remove(post);
            c.remove(post);
        }
        user.setPosts(temp);
        postAll.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public Post Like(Post post, Integer userID) {
        User user=userAll.getReferenceById(userID);
        List<Post> temp=user.getLiked();
        if(temp==null){
            temp=new ArrayList<>();
        }
        List<User> l=new ArrayList<>();
        if(post.getLikes()!=null)
             l= post.getLikes();

        if(temp.contains(post)){
            temp.remove(post);
            l.remove(user);
        }
        else {
            temp.add(post);
            l.add(user);
        }
        user.setLiked(temp);
        post.setLikes(l);
        postAll.save(post);
        userAll.save(user);
        return post;
    }

    @Override
    public Post comment(Post post, Integer userId,Comments comments) {
        User user=userAll.getReferenceById(userId);
        comments.setUserId(user.getId());
        comments.setPostId(post.getPostID());
        comments.setUser(user.getName().charAt(0));
        List<Post> temp=user.getCommented();
        List<Comments> l= post.getComments();
        if(temp.contains(post)){
            l.add(comments);
        }
        else {
            temp.add(post);
            l.add(comments);
        }
        commentsAll.save(comments);
        user.setCommented(temp);
        post.setComments(l);
        postAll.save(post);
        userAll.save(user);
        return post;
    }

    @Override
    public Post save(Post post, Integer userId) {
        User user=userAll.getReferenceById(userId);
        List<Post> temp=user.getSaved();
        if(temp==null){
            temp=new ArrayList<>();
        }
        List<User> l=new ArrayList<>();
        if(post.getSaved()!=null)
            l= post.getSaved();

        if(temp.contains(post)){
            temp.remove(post);
            l.remove(user);
        }
        else {
            temp.add(post);
            l.add(user);
        }

        user.setSaved(temp);
        userAll.save(user);

        post.setSaved(l);
        postAll.save(post);
        return post;
    }

    @Override
    public List<Post> Liked(Integer userId) {
        User user=userAll.getReferenceById(userId);
        return user.getLiked();
    }

    @Override
    public List<Post> Commented(Integer userId) {
        User user=userAll.getReferenceById(userId);
        return user.getCommented();
    }

    @Override
    public List<Post> Saved(Integer userId) {
        User user=userAll.getReferenceById(userId);
        return user.getSaved();
    }
    public List<Post> Users(Integer userId) {
        User user=userAll.getReferenceById(userId);
        return user.getPosts();
    }

    public List<Post> searchPost( String content) {
        List<Post> temp= postAll.searchByContent(content);
        return new ArrayList<>(temp);
    }
}
