package com.yupi.springbootinit.model.dto.interaction;


import com.yupi.springbootinit.service.InteractionService;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


@Component
@Getter
public class PromptConstant { // 动态生成一个BEAN, 获取表结构
    @Resource
    InteractionService interactionService;

    private String BUSINESS_TABLE_STRUCTURE;
    private String TABLE_STRUCTURE;
    private String DEFAULT_ROWS;

    @PostConstruct
    public void init() {
        // 建表语句
        TABLE_STRUCTURE = interactionService.getAllCreateTableSqlByDbName("bi");
        // 前端展示
        BUSINESS_TABLE_STRUCTURE = interactionService.getBusinessCreateTableSqlByDbName("bi");
        // 数据行
        DEFAULT_ROWS = interactionService.getAllDefaultRowsByDbName("bi");
    }
}
