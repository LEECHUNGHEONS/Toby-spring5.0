package main.vol1_chlee.ch4.lch.dao;

import main.vol1_chlee.ch4.lch.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // rowMapper 객체를 생성하기 위한 메소드
    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }

    // db에 user 추가
    public void add(final User user) throws DuplicateUserIdException {
        String sql = "INSERT INTO users(id, name, password) values(?,?,?)";
        this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
    }
}
