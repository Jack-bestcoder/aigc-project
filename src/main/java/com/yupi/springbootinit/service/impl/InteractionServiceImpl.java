package com.yupi.springbootinit.service.impl;

import com.yupi.springbootinit.mapper.InteractionMapper;
import com.yupi.springbootinit.model.dto.interaction.UpdateRequest;
import com.yupi.springbootinit.model.entity.UserQuery;
import com.yupi.springbootinit.service.InteractionService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class InteractionServiceImpl implements InteractionService {

    @Resource
    private InteractionMapper interactionMapper;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getAllCreateTableSqlByDbName(String dbName) {
        List<String> tableNameList = interactionMapper.selectAllTableNamesByDbName(dbName);
        List<String> createTableSqlList = new ArrayList<>(Collections.emptyList());
        tableNameList.forEach(tableName -> {
            String sql = "SHOW CREATE TABLE " + dbName + "." + tableName;
            List<Map<String, Object>> result = interactionMapper.executeSql(sql);
            if (!result.isEmpty()) {
                Map<String, Object> createTableStatement = result.get(0);
                String createTableSql = (String) createTableStatement.get("Create Table");
                createTableSqlList.add(createTableSql);
            }
        });
        return String.join("\n", createTableSqlList);
    }

    @Override
    public String getAllDefaultRowsByDbName(String dbName) {
        List<String> tableNameList = interactionMapper.selectAllTableNamesByDbName(dbName);
        StringBuilder allRowsStringBuilder = new StringBuilder();

        tableNameList.forEach(tableName -> {
            // 构造查询每个表前几行数据的SQL语句
            String querySql = "SELECT * FROM " + dbName + "." + tableName + " LIMIT 3";

            // 执行SQL语句并获取结果集
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql);

            // 检查是否有数据行，如果有则格式化输出
            if (!rows.isEmpty()) {
                allRowsStringBuilder.append(tableName).append(" (");

                // 遍历列名，只获取一次即可（假设所有行都有相同的列）
                List<String> columnNames = new ArrayList<>(rows.get(0).keySet());

                // 遍历结果集，并将每行数据格式化为字符串
                for (int i = 0; i < rows.size(); i++) {
                    Map<String, Object> row = rows.get(i);

                    allRowsStringBuilder.append("[");

                    for (int j = 0; j < columnNames.size(); j++) {
                        String columnName = columnNames.get(j);
                        Object value = row.get(columnName);
                        allRowsStringBuilder.append(value != null ? value.toString() : "NULL");

                        if (j < columnNames.size() - 1) {
                            allRowsStringBuilder.append(", ");
                        }
                    }

                    allRowsStringBuilder.append("]");

                    if (i < rows.size() - 1) {
                        allRowsStringBuilder.append(", ");
                    }
                }

                allRowsStringBuilder.append(");");

                // 在每个表的数据之后添加一个换行符作为分隔
                allRowsStringBuilder.append("\n");
            }
        });

        // 返回包含所有表前几行数据的格式化字符串
        return allRowsStringBuilder.toString();
    }

    @Override
    public void insertUserQuery(UserQuery userQuery) {
        interactionMapper.insertUserQuery(userQuery);
    }

    @Override
    public List<UserQuery> getUserQueryByUserIdWithCursor(long userId, long cursor, int num) {
        List<UserQuery> userQueryList = interactionMapper.selectUserQueryByUserIdWithCursor(userId, cursor, num);
        if (userQueryList == null || userQueryList.isEmpty()) {
            return Collections.emptyList();
        }
        return userQueryList;
    }

    @Override
    public void updateUserQuery(UpdateRequest updateRequest) {
        interactionMapper.updateGenerateSqlById(updateRequest.getId(), updateRequest.getGenerateSql());
    }
}
