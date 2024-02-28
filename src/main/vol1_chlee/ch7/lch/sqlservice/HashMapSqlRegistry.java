package main.vol1_chlee.ch7.lch.sqlservice;

import main.vol1_chlee.ch7.lch.sqlservice.exception.SqlNotFoundException;
import main.vol1_chlee.ch7.lch.sqlservice.exception.SqlRetrievalFailureException;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {
    // SqlRegistry 구현부를 분리
    private Map<String, String> sqlMap = new HashMap<String, String>();

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if (sql == null) throw new SqlRetrievalFailureException(key + ": SQL을 찾을 수 없습니다.");
        else return sql;
    }

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }
}
