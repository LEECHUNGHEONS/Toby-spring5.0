package main.vol1_chlee.ch3.user.dao;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
