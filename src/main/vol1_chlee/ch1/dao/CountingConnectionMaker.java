package main.vol1_chlee.ch1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker  {
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    // 생성자
    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realConnectionMaker.makeConnection();

    }

    // counter getter
    public int getCounter() {
        return this.counter;
    }
}
