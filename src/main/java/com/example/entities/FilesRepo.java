package com.example.entities;

import com.example.usersData.FileUpload;
import com.example.usersData.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepo extends JpaRepository<FileUpload, Long> {
    FileUpload findByName(String name);
    FileUpload findByAuthor(User user);
}
