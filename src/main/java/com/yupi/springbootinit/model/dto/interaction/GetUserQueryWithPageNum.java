package com.yupi.springbootinit.model.dto.interaction;

import lombok.Data;

@Data
public class GetUserQueryWithPageNum {
    Long useId;
    Integer pageNum;
    Integer pageSize;
}
