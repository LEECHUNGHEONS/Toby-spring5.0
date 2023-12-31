package main.vol1_chlee.ch2.dao;

import main.vol1_chlee.ch2.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDaoTest {

    private UserDao dao;

    // db 커넥션 셋업
    @BeforeEach
    public void setUp(){
        System.out.println("setUp() :" + this);

        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mariadb://localhost:3307/toby?characterEncoding=UTF-8",
                "root",
                "1234",
                true
        );
        dao = new UserDao();
        dao.setDataSource(dataSource);
    }

    @Test // junit을 쓰겠다는 어노테이션
    public void addAndGet() throws ClassNotFoundException, SQLException{
        System.out.println("addAndGet() :" + this);
        dao.deleteAll(); // 테스트 하기 전에 db 비우기 이유는 테스트의 일관성을 위해 같은 내용이 db에 있으면 오류가 뜨니..
        assertEquals(dao.getCount(),0);

        User user1 = new User("user1","one","1111");
        User user2 = new User("user2","two","2222");

        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        User userget1 = dao.get(user1.getId());
        assertEquals(userget1.getName(),user1.getName());
        assertEquals(userget1.getPassword(),user1.getPassword());

        User userget2 = dao.get(user2.getId());
        assertEquals(userget2.getName(), user2.getName());
        assertEquals(userget2.getPassword(), user2.getPassword());
        assertEquals(userget2.getPassword(), user2.getPassword());

    }

}
