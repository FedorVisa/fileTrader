package com.example.service;

import com.example.usersData.FileUpload;
import com.example.usersData.User;
import com.example.utility.ImageMimeTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileUploadingService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private SessionRegistry sessionRegistry;
    private void createDir(Path path) throws Exception {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new Exception("Could not initialize storage", e);
        }
    }

    private void checkFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("Failed to store empty file.");
        }
        if (file.getOriginalFilename() == null) {
            throw new Exception("Failed to store file with empty name");
        }
    }

    public void storeFile(MultipartFile file, Path dirPath) throws Exception {
        checkFile(file);
        createDir(dirPath);
        Path filePath = dirPath.resolve( // uploadPath + а надо ли туту полный путь?
                Paths.get( file.getOriginalFilename() + ImageMimeTypeUtil.getExtension(file.getContentType())));
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new Exception("Failed to store file.");
        }
    }

    public void getFileFor( String filename)
    {
        return ;
    }

}
