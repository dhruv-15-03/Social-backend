package com.example.demo.Classes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private char user;
    private Integer userId;
    private Integer postId;
    private String comment;
    @SuppressWarnings("JpaAttributeTypeInspection")
    private List<String> replies=new ArrayList<>();

    private Integer likes;

    public Comments() {
    }

    public Comments(Integer id, Integer userId, Integer postId, String comment) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
    }

    public Comments(Integer id, String comment, List<String> replies, Integer likes) {
        this.id = id;
        this.comment = comment;
        this.replies = replies;
        this.likes = likes;
    }

}
