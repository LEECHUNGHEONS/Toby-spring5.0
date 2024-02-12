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

    @Bean
    public TransactionAdvice transactionAdvice() {
        TransactionAdvice advice = new TransactionAdvice();
        advice.setTransactionManager(transactionManager());
        return advice;
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
//		NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
//		//pointcut.setMappedClassName("*Service");
//		pointcut.setMappedName("upgrade*");

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    @Bean
    public NameMatchClassMethodPointcut transactionClassPointcut() {
        NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
        pointcut.setMappedClassName("*ServiceImpl");
        pointcut.setMappedName("upgrade*");
        return pointcut;

    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(transactionAdvice());
        advisor.setPointcut(transactionClassPointcut());
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userDao());
        userServiceImpl.setMailSender(mailSender());
        return userServiceImpl;
    }

    @Bean
    public UserService userServiceImpl() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userDao());
        userServiceImpl.setMailSender(mailSender());
        return userServiceImpl;
    }

    @Bean
    public UserService testUserService() {
        TestUserServiceImpl testuserServiceImpl = new TestUserServiceImpl();
        testuserServiceImpl.setUserDao(userDao());
        testuserServiceImpl.setMailSender(mailSender());
        return testuserServiceImpl;
    }

    //==  UserServiceTest 클래스 내의 static 클래스로 정의되었다면 아래와 같이 정의해야 한다 ==//
//	@Bean
//	@Qualifier("testUserService")
//	public UserServiceImpl testUserService() {
//	    UserServiceImpl testUserServiceImpl = new UserServiceTest.TestUserServiceImpl();
//	    testUserServiceImpl.setUserDao(userDao());
//	    testUserServiceImpl.setMailSender(mailSender());
//	    return testUserServiceImpl;
//	}


}
