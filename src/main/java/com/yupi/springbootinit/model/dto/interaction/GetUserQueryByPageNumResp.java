package com.yupi.springbootinit.model.dto.interaction;

import com.yupi.springbootinit.model.entity.UserQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class GetUserQueryByPageNumResp {
    List<UserQuery> userQueryList;
    Integer total;
}
