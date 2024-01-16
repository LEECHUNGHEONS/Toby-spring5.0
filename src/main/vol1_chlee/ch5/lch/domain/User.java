package main.vol1_chlee.ch5.lch.domain;

public class User {

    String id;
    String name;
    String password;

    int login;
    int recommend;
    String email;

    public User() {
    }

    public User(String id, String name, String password, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;

        this.login = login;
        this.recommend = recommend;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEamil(String email) {
        this.email = email;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
