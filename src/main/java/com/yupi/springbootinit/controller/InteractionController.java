package com.yupi.springbootinit.controller;


import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.chart.ChartAddRequest;
import com.yupi.springbootinit.model.dto.interaction.SqlExecuteRequest;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.SqlExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interact")
@Slf4j
public class InteractionController {

    @Resource
    SqlExecuteService sqlExecuteService;

    @PostMapping("/executeSql")
    public BaseResponse<List<Map<String, Object>>> executeSql(@RequestBody SqlExecuteRequest sqlExecuteRequest, HttpServletRequest request) throws Exception {
        try {
            // 调用服务层方法来执行SQL
            List<Map<String, Object>> result = sqlExecuteService.executeSql(sqlExecuteRequest.getSql());

            // 假设我们返回受影响的行数，或者根据实际需求返回其他内容
            Long affectedRows = (result != null) ? Long.valueOf(result.size()) : 0L;

            // 构建响应对象
            BaseResponse<List<Map<String, Object>>> response = new BaseResponse<>(200, result, "执行成功");
            response.setData(result);
            response.setCode(200); // 假设200表示成功
            response.setMessage("SQL执行成功");
            return response;
        } catch (Exception e) {
            // 构建错误响应对象
            BaseResponse<List<Map<String, Object>>> response = new BaseResponse<>(500, null, "SQL执行失败: " + e.getMessage());
            return response;
        }
    }

}
