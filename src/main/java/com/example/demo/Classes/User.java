package com.example.demo.Classes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String userName;
    private String password;
    private String gender;
    private String profile;
    private String bio;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> followers=new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> following=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Story> story=new ArrayList<>();
    @ManyToMany(mappedBy = "chats")
    @JsonIgnore
    private List<Chat> chat=new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> Liked=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> Saved=new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> Commented=new ArrayList<>();

    public User(Integer id, String name, String email, String userName,String password,String gender,String profile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password=password;
        this.gender=gender;
        this.profile=profile;
    }
    public User() {
    }
}
