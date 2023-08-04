package main.vol1_chlee.ch5.lch.dao;

import main.vol1_chlee.ch5.lch.domain.Level;
import main.vol1_chlee.ch5.lch.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // rowMapper 객체를 생성하기 위한 람다로 정의한 메소드
    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEamil(rs.getString("email"));
            return user;
        });
    }

    // db에 user 추가
    public void add(final User user) {
        String sql = "INSERT INTO users(id, name, password, level, login, recommend, email) " +
                "VALUES(?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend(),
                user.getEmail());
    }

    // db에서 Id에 해당하는 user 정보 검색
    public Optional<User> get(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Stream<User> stream = jdbcTemplate.queryForStream(sql, userRowMapper(), id)) {
            return stream.findFirst();
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    // 테이블 모든 걸 삭제!!!!
    public void deleteAll()  {
        String sql = "DELETE FROM users";
        // 콜백을 jdbc 내장함수가 해줌
        this.jdbcTemplate.update(sql);
    }

    // 해당 테이블 내에 개수 조회
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM users";

        List<Integer> result = jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt(1));

        return (int) DataAccessUtils.singleResult(result);
    }

    //테이블에 있는 모든 user 데이터 가져오기
    public List<User> getAll() {
        String sql = "SELECT * FROM users ";

        return this.jdbcTemplate.query(sql,userRowMapper());
    }

    // User 사용자 정보 업데이트 및 수정
    public void update(final User user) {
        String sql = "UPDATE users SET name=?, "
                + "password=?, "
                + "level=?, "
                + "login=?, "
                + "recommend=?, "
                + "email=? "
                + "WHERE id=?";

        this.jdbcTemplate.update(sql, user.getName()
                , user.getPassword()
                , user.getLevel().intValue()
                , user.getLogin()
                , user.getRecommend()
                , user.getEmail()
                , user.getId());
    }
}
