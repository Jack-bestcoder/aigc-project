package com.yupi.springbootinit.model.entity;


import lombok.Data;

import java.util.Date;

@Data
public class UserQuery {
    Long id;
    Long userId;
    String prompt;
    String generate_sql;
    Date createTime;
    Date updateTime;
    int status;
}
