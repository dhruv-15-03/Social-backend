package com.example.demo.DataBase;

import com.example.demo.Classes.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository

public interface PostAll extends JpaRepository<Post,Integer> {
    @Query("select p from Post p where p.Caption LIKE %:content%")
    public List<Post> searchByContent(@Param("content") String content);

    @Query("SELECT p FROM Post p WHERE LENGTH(p.video) > 2")
    public List<Post> reels();
    @Query("SELECT p FROM Post p WHERE LENGTH(p.video) > 2 AND p.user.id = :id")
    public List<Post> userReels(@Param("id") Integer id);
}
