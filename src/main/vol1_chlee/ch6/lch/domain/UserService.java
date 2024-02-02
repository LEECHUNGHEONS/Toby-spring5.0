package main.vol1_chlee.ch6.lch.domain;

public interface UserService {
// 트랜잭션과 비즈니스 로직을 분리하기 위한 메소드와 인터페이스!

    void add(User user);
    void upgradeLevels();

}
