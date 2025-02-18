package com.example.demo.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private String image;
    private String video;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User main;
    @ManyToMany
    @JsonIgnore
    private List<User> liked=new ArrayList<>();
    @ManyToMany
    @JsonIgnore
    private List<User> views=new ArrayList<>();
    private LocalDateTime time;

    public Story() {
    }

    public Story(Integer id, String content,String image,String video, User main, List<User> liked,LocalDateTime time,List<User> views) {
        this.id = id;
        this.content = content;
        this.main = main;
        this.liked = liked;
        this.time=time;
        this.views=views;
        this.image=image;
        this.video=video;
    }
}
