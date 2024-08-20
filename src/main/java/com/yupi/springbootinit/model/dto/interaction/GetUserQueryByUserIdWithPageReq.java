package com.yupi.springbootinit.model.dto.interaction;

import lombok.Data;

@Data
public class GetUserQueryByUserIdWithPageReq {
    private long userId;
    private long cursor;
    private int num;
}
