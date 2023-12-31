package main.vol1_chlee.ch2.learningtest;


import main.vol1_chlee.ch2.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ApplicationContextTest {

    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        return userDao;
    }
}
