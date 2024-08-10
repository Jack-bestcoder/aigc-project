package com.yupi.springbootinit.service;
import java.util.List;
import java.util.Map;


public interface SqlExecuteService {

    List<Map<String, Object>> executeSql(String sql) throws Exception;
}
