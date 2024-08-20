package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.dto.interaction.UpdateRequest;
import com.yupi.springbootinit.model.entity.UserQuery;

import java.util.List;

public interface InteractionService {

    String getAllCreateTableSqlByDbName(String dbName);
    String getAllDefaultRowsByDbName(String dbName);
    void insertUserQuery(UserQuery userQuery);
    List<UserQuery> getUserQueryByUserIdWithCursor(long userId, long cursor, int num);
    void updateUserQuery(UpdateRequest updateRequest);
}
