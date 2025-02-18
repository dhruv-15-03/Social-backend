package com.example.demo.Classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer PostID;

    private String image;
    private String video;
    private String post;
    private String Caption;
    @ManyToMany
    private List<User> Likes;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Comments> comments;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<User> saved;
    @SuppressWarnings("JpaAttributeTypeInspection")
    public List<String> tags=new ArrayList<>();

    public Post() {
        Likes=new ArrayList<>();
        saved=new ArrayList<>();
    }

    public Post(Integer postID, String post,String caption,String image,String video) {
        PostID = postID;
        this.post = post;
        Caption = caption;
        Likes=new ArrayList<>();
        saved=new ArrayList<>();
        this.image=image;
        this.video=video;
    }

    public Post(Integer postID, String image, String video, String post, String caption, List<User> likes, List<Comments> comments, User user, List<User> saved, List<String> tags) {
        PostID = postID;
        this.image = image;
        this.video = video;
        this.post = post;
        Caption = caption;
        Likes = likes;
        this.comments = comments;
        this.user = user;
        this.saved = saved;
        this.tags = tags;
    }
}
