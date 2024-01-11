package main.vol1_chlee.ch4.lch.dao;

public class DuplicateUserIdException extends RuntimeException {

    public DuplicateUserIdException(Throwable cause){
        super(cause);
    }
}
