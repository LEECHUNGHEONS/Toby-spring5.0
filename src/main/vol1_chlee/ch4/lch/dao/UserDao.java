package main.vol1_chlee.ch4.lch.dao;

import main.vol1_chlee.ch4.lch.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);

    Optional<User> get(String id);

    List<User> getAll();

    void deleteAll();

    int getCount();
}
