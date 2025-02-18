package com.example.demo.DataBase;

import com.example.demo.Classes.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface CommentsAll extends JpaRepository<Comments,Integer> {
}
