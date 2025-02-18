package com.example.demo.DataBase;

import com.example.demo.Classes.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageAll extends JpaRepository<Messages,Integer> {
    @Query("SELECT u from Messages u where u.message like %:query%")
    public List<Messages> findByMessage(@Param("query") String query);
}
