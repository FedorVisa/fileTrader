package com.example.service;

import com.example.entities.UsersRepo;
import com.example.usersData.Role;
import com.example.usersData.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UsersRepoService implements UserDetailsService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public User findByName(String name) throws UsernameNotFoundException, NullPointerException{

       if(name != null && !name.isEmpty()) {
           System.out.println(name);
           //usersRepo.findAll().get(0).getUsername();
           User user = usersRepo.findByUsername(name);
           System.out.println(name + "1");
           if(user !=null )  return user;
           else throw  new UsernameNotFoundException(" User not found");
       }
       throw new NullPointerException("name is NULL");
    }
    @Transactional
    public void addNewUser (User user) throws NullPointerException{
        try {
            User newUser = findByName(user.getUsername());
        } catch (UsernameNotFoundException e){
            user.setRoles(Collections.singleton(Role.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepo.save(user);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User  user =  findByName(username);
       // System.out.println(user.getUsername());
       // System.out.println(username);
        return user;
    }
}
