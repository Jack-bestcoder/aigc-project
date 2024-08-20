package com.yupi.springbootinit.model.dto.interaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class SqlGeneration {
    private String createTableSqlList;
    private String defaultRows;
    private String prompt;
    private long userId;
    private long id;
}
