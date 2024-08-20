package com.yupi.springbootinit.model.vo;

import lombok.Data;

@Data
public class QueryVO {
    private Integer pageNum;
    private Integer limit;
    private String keyword;
}
