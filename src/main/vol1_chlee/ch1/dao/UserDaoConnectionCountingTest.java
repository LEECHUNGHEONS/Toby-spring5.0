package main.vol1_chlee.ch1.dao;

import main.vol1_chlee.ch1.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 앞에서 main() 메소드와 같을 경우 DL로 객체를 주입 받음!
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("Counting");
        user.setName("chlee130");
        user.setPassword("mariaDB");
        dao.add(user);
        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        ccm.makeConnection();
        ccm.makeConnection();
        ccm.makeConnection();

        System.out.println("Connection counter: " + ccm.getCounter());



    }
}
