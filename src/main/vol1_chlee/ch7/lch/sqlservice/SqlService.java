package main.vol1_chlee.ch7.lch.sqlservice;


import main.vol1_chlee.ch7.lch.sqlservice.exception.SqlRetrievalFailureException;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
