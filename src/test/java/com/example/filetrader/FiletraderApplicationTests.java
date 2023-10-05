package com.example.filetrader;

import com.example.service.UsersRepoService;
import com.example.usersData.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;


@SpringBootTest
class FiletraderApplicationTests {

    @Autowired
    private UsersRepoService usersRepoService;
    @Test
    void contextLoads() {
        User user = new User("alex", "123456");
        assertEquals(user.getUsername(), "alex");
    }

}
