package main.vol1_chlee.ch3.user.dao;

import main.vol1_chlee.ch3.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserDaoTest {

    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        System.out.println("setUp():" + this);


        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mariadb://localhost:3307/toby_study?characterEncoding=UTF-8",
                "root",
                "1234",
                true);
        dao = new UserDao();
        dao.setDataSource(dataSource);

        user1 = new User("user1", "one", "1111");
        user2 = new User("user2", "two", "2222");
        user3 = new User("user3", "three", "3333");
    }

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
        System.out.println("addAndGet(): " + this);
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        Optional<User> Optuserget1 = dao.get(user1.getId());
        if(!Optuserget1.isEmpty()) {
            User userget = Optuserget1.get();
            assertEquals(user1.getName(), userget.getName());
            assertEquals(user1.getPassword(), userget.getPassword());
        }

        Optional<User> Optuserget2 = dao.get(user2.getId());
        if(!Optuserget2.isEmpty()) {
            User userget = Optuserget2.get();
            assertEquals(user2.getName(), userget.getName());
            assertEquals(user2.getPassword(), userget.getPassword());
        }
    }

    @Test
    public void count() throws ClassNotFoundException, SQLException {
        System.out.println("count(): " + this);

        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        assertEquals(dao.getCount(), 1);

        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        dao.add(user3);
        assertEquals(dao.getCount(), 3);
    }

    @Test
    public void getUserFailure() throws SQLException{
        System.out.println("getUserFailure(): " + this);
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        //assertThrows(EmptyResultDataAccessException.class, () -> {dao.get("unknown_id");});
        Optional<User> Optuserget = dao.get("unknown_id");
        assertTrue(Optuserget.isEmpty());
    }

    @Test
    public void getAll() throws ClassNotFoundException, SQLException{
        dao.deleteAll();

        // 네거티브 테스트, DB에 아무런 정보가 없을 때 검증 코드가 추가됐다
        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertEquals(users1.size(), 1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertEquals(users2.size(), 2);
        checkSameUser(user2, users2.get(0));
        checkSameUser(user1, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertEquals(users3.size(), 3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user1, users3.get(2));

    }

    private void checkSameUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getPassword(), user2.getPassword());
    }


}
