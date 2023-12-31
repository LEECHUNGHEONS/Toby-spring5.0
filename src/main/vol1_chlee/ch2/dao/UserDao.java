package main.vol1_chlee.ch2.dao;

import main.vol1_chlee.ch2.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException{
        Connection con = dataSource.getConnection();

        //Prepare statements 사용
        String sql = "INSERT INTO users(id,name,password) values(?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1,user.getId());
        pst.setString(2,user.getName());
        pst.setString(3,user.getPassword());

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{
        Connection con = dataSource.getConnection();

        String sql = "SELECT * FROM users WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1,id);

        ResultSet rs = pst.executeQuery();
        User user = new User();
        if(rs.next()){
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        pst.close();
        con.close();

        return user;
    }

    // 테이블 정보 삭제
    public void deleteAll() throws SQLException{
        Connection con = dataSource.getConnection();

        String sql = "DELETE FROM users";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.executeUpdate();

        pst.close();
        con.close();
    }

    // 테이블 정보 개수 조회
    public int getCount() throws SQLException {
        Connection con = dataSource.getConnection();

        String sql = "SELECT COUNT(*) FROM users";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int result = rs.getInt(1);

        rs.close();
        pst.close();
        con.close();

        return result;
    }


}
