package main.vol1_chlee.ch7.lch.sqlservice;

import main.vol1_chlee.ch7.lch.sqlservice.exception.SqlNotFoundException;

public interface SqlRegistry {
	
	void registerSql(String key, String sql);
	
	String findSql(String key) throws SqlNotFoundException;

}
