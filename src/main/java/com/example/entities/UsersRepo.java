package com.example.entities;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.usersData.User;


public interface UsersRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);

}
