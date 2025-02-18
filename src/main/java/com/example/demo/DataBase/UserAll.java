package com.example.demo.DataBase;

import com.example.demo.Classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
@org.springframework.stereotype.Repository
public interface UserAll extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.name like %:query or u.userName like %:query%")
    public List<User> searchUser(@Param("query") String query);
    @Query("SELECT u FROM User u where u.userName LIKE (:query)")
    public User searchByUserName(@Param("query") String query);
}
