package main.vol1_chlee.ch1.dao;

import main.vol1_chlee.ch1.domain.User;
import main.vol1_chlee.ch1.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

//        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//        UserDao dao = context.getBean("userDao", UserDao.class);
//        ConnectionMaker connectionMaker = new DConnectionMaker();
//
//        UserDao dao = new UserDao(connectionMaker);

        UserDao dao = new DaoFactory().userDao();


        User user = new User();
        user.setId("chlee123");
        user.setName("leech3");
        user.setPassword("12343");

        dao.add(user);

        System.out.println(user.getId() + "등록성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공");


    }
}
