package main.vol1_chlee.ch3.user.domain;

public class User {
    String id;
    String name;
    String password;

    // 기본 생성자
    public User() {}

    //테스트를 쉽게 하기 위해 파라미터가 있는 생성자
    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
