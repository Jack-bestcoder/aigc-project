package com.yupi.springbootinit.service.impl;

import com.yupi.springbootinit.mapper.InteractionMapper;
import com.yupi.springbootinit.service.SqlExecuteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SqlExecuteServiceImpl implements SqlExecuteService {
    @Resource
    private InteractionMapper interactionMapper;

    @Override
    public List<Map<String, Object>> executeSql(String sql) throws Exception {
        // 调用 Mapper 接口中的方法执行 SQL
        return interactionMapper.executeSql(sql);
    }
}