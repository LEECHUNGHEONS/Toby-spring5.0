package main.vol1_chlee.ch3.user.dao;

import main.vol1_chlee.ch3.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy  {

    User user;
    public AddStatement(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection con) throws SQLException {

        String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, user.getId());
        pst.setString(2, user.getName());
        pst.setString(3, user.getPassword());

        return pst;
    }
}
