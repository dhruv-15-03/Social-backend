package com.example.demo.Implementation;

import com.example.demo.Classes.Comments;
import com.example.demo.Classes.Post;
import com.example.demo.Exception.PostException;

import java.util.List;

public interface PostMethods {
    public Post newPost(Post post, Integer userId);
    public Post getPost(Integer postId);
    public String DeletePost(Post post, Integer userId) throws PostException;
    public Post Like(Post post,Integer userID);
    public Post comment(Post post, Integer userId, Comments comments);
    public Post save(Post post,Integer userId);
    public List<Post> Liked(Integer userId);
    public List<Post> Commented(Integer userId);
    public List<Post> Saved(Integer userId);
    public List<Post> searchPost( String content);

}
