package com.yupi.springbootinit.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value ="u_user_query")
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
