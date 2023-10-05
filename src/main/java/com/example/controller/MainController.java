package com.example.controller;


import com.example.entities.UsersRepo;
import com.example.service.FileUploadingService;
import com.example.usersData.FileUpload;
import com.example.usersData.User;
import com.example.utility.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Value("${upload.path}")
    private Path uploadPath;
    @Autowired
    private FileUploadingService fileUploadingService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String post(){
        return "login";
    }



//    @PostMapping("/main")
//    public String addFile(FileUpload fileUpload, User user, Map<String, Object> model, @RequestParam("file") MultipartFile file ) throws Exception{
//        try{
//            if( user == null){
//                model.put("message", "you need to be authorized to upload files");
//                return "main";
//            }
//            fileUploadingService.storeFile(file, uploadPath);
//            storageService.store(file);
//            fileUpload.setName(file.getOriginalFilename());
//            fileUpload.setAuthor(user);
//            //user.addToUpFiles(fileUpload);model.put("message", MvcUriComponentsBuilder. )
//            model.put("message", fileUpload.getName());
//        } catch ( Exception e){
//            model.put("message", " File uploading error");
//        };
//        return "main";
//    }

    @PostMapping("/qqq")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, FileUpload fileUpload, User user, Map<String, Object> model) throws Exception {
        if( user == null){
               model.put("message", "you need to be authorized to upload files");
                return "main";
            }

        storageService.store(file);
        fileUploadingService.storeFile(file, uploadPath);
                    fileUpload.setName(file.getOriginalFilename());
           fileUpload.setAuthor(user);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/main";
    }



    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("/main")
    public String UploadFilesToPage(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(MainController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }


}