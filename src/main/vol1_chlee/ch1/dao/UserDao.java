package main.vol1_chlee.ch1.dao;

import main.vol1_chlee.ch1.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        DaoFactory daoFactory = new DaoFactory();
        this.connectionMaker = daoFactory.connectionMaker();
    }

    public UserDao () {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
    }
//
//    //== DataSource 사용하기 ==//
//    private DataSource dataSource;

//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    public void add(User user) throws ClassNotFoundException, SQLException{
        Connection con =  getConnection();

        //프리페어 스테이트먼츠 사용!
        String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, user.getId());
        pst.setString(2, user.getName());
        pst.setString(3, user.getPassword());

        pst.executeUpdate();

        pst.close();
        con.close();

    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();

        String sql = "SELECT * FROM users WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, id);

        ResultSet rs = pst.executeQuery();
        User user = new User();
        if (rs.next()) {
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        pst.close();
        con.close();

        return user;

    }

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        String className = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mariadb://localhost:3307/toby_study?characterEncoding=UTF-8";
        String userId = "root";
        String password = "1234";

        Class.forName(className);
        Connection con = DriverManager.getConnection(url, userId, password);

        return con;
    }
}
