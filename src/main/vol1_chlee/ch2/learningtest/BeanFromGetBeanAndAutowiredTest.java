package main.vol1_chlee.ch2.learningtest;

import main.vol1_chlee.ch2.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationContextTest.class)
public class BeanFromGetBeanAndAutowiredTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDao userDao;

    @Test
    public void getUserDaoTest(){
        UserDao beanFromAutowired = userDao;
        UserDao beanFromApplicationContext = context.getBean("userDao",UserDao.class);

        assertSame(beanFromAutowired,beanFromApplicationContext);
    }
}
