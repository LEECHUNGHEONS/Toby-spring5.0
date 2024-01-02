package main.vol1_chlee.ch1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        //N,D사의 독자적인 방법으로 Connection을 생성하는 코드
        String className = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mariadb://localhost:3307/toby_study?characterEncoding=UTF-8";
        String userId = "root";
        String password = "1234";

        Class.forName(className);
        Connection nCon = DriverManager.getConnection(url, userId, password);

        return nCon;
    }

}
