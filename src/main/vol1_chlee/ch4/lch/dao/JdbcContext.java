package main.vol1_chlee.ch4.lch.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
    템플리 메소드 , 인터페이스 타입으로 익명 내부 클래스를 구현한 객체를 매개변수로 받음
     */

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = dataSource.getConnection();

            // 콜백 메소드 호출하고 파라미터로 커넥션 전달
            pst = stmt.makePreparedStatement(con);

            pst.executeUpdate();


        }catch (SQLException e){
            throw e;
        }finally { // 리소스를 계속 갉아 먹기에 무조건 닫아줘야하니 에러가 나든 뭐든 결국 닫아주기!
            if (pst != null) { try {pst.close(); } catch (SQLException e) {} }
            if (con != null) { try {con.close(); } catch (SQLException e) {} }
        }

    }

    public void executeSql(final String query) throws SQLException {

        workWithStatementStrategy(con -> con.prepareStatement(query));
    }

}

