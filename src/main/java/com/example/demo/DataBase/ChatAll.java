package com.example.demo.DataBase;

import com.example.demo.Classes.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatAll extends JpaRepository<Chat,Integer> {
    @Query("SELECT u from Chat u join u.chats d where d.userName=:query OR u.admin.userName = :query")
    public List<Chat> findChatByUser(@Param("query") String query);
}
