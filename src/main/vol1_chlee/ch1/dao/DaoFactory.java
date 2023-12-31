package main.vol1_chlee.ch1.dao;

import main.vol1_chlee.ch1.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        String url = "jdbc:mariadb://localhost:3307/toby_study?characterEncoding=UTF-8";
        String username = "root";
        String password = "1234";

        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }


}
