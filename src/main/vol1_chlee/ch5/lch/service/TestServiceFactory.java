package main.vol1_chlee.ch5.lch.service;

import main.vol1_chlee.ch5.lch.dao.UserDaoJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

//빈 설정 정보 클래스
@Configuration
public class TestServiceFactory {

    @Bean
    public UserDaoJdbc userDao(){
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }


    @Bean
    public DataSource dataSource() {
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

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }


    @Bean
    public UserService userService() {
        UserService userService = new UserService();
        userService.setUserDao(userDao());
        userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy());
        userService.setTransactionManager(transactionManager());
        return userService;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        UserLevelUpgradeImpl userLevelUpgradePolicy = new UserLevelUpgradeImpl();
        userLevelUpgradePolicy.setMailSender(new DummyMailSender());
        return userLevelUpgradePolicy;
    }

}
