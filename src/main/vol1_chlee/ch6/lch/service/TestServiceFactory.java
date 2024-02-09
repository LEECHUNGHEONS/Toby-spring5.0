package main.vol1_chlee.ch6.lch.service;

import main.vol1_chlee.ch6.lch.dao.UserDao;
import main.vol1_chlee.ch6.lch.dao.UserDaoJdbc;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class TestServiceFactory {

    @Bean
    public UserDao userDao() {
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
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public DummyMailSender mailSender() {
        DummyMailSender dummyMailSender = new DummyMailSender();
        return dummyMailSender;
    }


}
