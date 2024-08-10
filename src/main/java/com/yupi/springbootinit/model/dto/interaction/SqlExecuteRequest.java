package com.yupi.springbootinit.model.dto.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlExecuteRequest {
    String sql;
}
