package com.yupi.springbootinit.model.dto.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRequest {
    long id;
    String generateSql;

}
