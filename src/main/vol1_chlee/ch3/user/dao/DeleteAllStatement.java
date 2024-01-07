package main.vol1_chlee.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{

    // jdbc 쓰기 이전 deleteAll() 메소드에서 사용될 템플릿 메소드
    @Override
    public PreparedStatement makePreparedStatement(Connection con) throws SQLException {
        String sql = "DELETE FROM users";
        PreparedStatement pst = con.prepareStatement(sql);
        return pst;


    }
}
