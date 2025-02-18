package com.example.demo.DataBase;

import com.example.demo.Classes.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryAll extends JpaRepository<Story,Integer> {
}
