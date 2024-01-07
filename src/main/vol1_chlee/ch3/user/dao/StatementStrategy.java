package main.vol1_chlee.ch3.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

    // 전략패턴에서 사용될 메소드
    PreparedStatement makePreparedStatement(Connection con) throws SQLException;
}
