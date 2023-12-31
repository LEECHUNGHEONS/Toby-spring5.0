package main.vol1_chlee.ch1.dao;

import main.vol1_chlee.ch1.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setDataSource();
        return userDao;
    }


}
