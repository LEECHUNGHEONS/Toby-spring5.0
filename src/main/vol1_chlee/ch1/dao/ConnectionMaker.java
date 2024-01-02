package main.vol1_chlee.ch1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    //== 인터페이스는 사용할 기능만 정의한다 ==//
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
