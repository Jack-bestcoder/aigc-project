package com.yupi.springbootinit.controller;


import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.bizmq.BiMessageProducer;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.model.dto.interaction.*;
import com.yupi.springbootinit.model.entity.UserQuery;
import com.yupi.springbootinit.service.InteractionService;
import com.yupi.springbootinit.service.SqlExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interact")
@Slf4j
public class InteractionController {

    @Resource
    SqlExecuteService sqlExecuteService;

    @Resource
    InteractionService interactionService;

    @Resource
    BiMessageProducer biMessageProducer;

    @Resource
    PromptConstant promptConstant;

    @PostMapping("/executeSql")
    public BaseResponse<List<Map<String, Object>>> executeSql(@RequestBody SqlExecuteRequest sqlExecuteRequest, HttpServletRequest request) throws Exception {
        try {
            // 调用服务层方法来执行SQL
            List<Map<String, Object>> result = sqlExecuteService.executeSql(sqlExecuteRequest.getSql());
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

    @PostMapping("/getSqlByPrompt")
    @Transactional
    public BaseResponse<String> getSqlByPrompt(@RequestBody GetSqlByPromptRequest getSqlByPromptRequest, HttpServletRequest request) throws Exception {
        try {
            // 查建表语句
            String createTableSqlList = promptConstant.getTABLE_STRUCTURE();
            // 查默认记录
            String defaultRows = promptConstant.getDEFAULT_ROWS();
            // 获取prompt
            String prompt = getSqlByPromptRequest.getPrompt();
            // userid
            long userId = getSqlByPromptRequest.getUserId();
            // 组装为json
            SqlGeneration sqlGeneration = new SqlGeneration(createTableSqlList, defaultRows, prompt, userId, 0);
            // 落db
            UserQuery userQuery = new UserQuery();
            userQuery.setUserId(userId);
            userQuery.setPrompt(prompt);
            interactionService.insertUserQuery(userQuery);
            sqlGeneration.setId(userQuery.getId());
            System.out.println(userQuery.getId());
            // 发消息异步执行
            // 使用hutool的JSON工具类将对象转换为JSON字符串
            String jsonRequest = JSONUtil.toJsonStr(sqlGeneration);
            biMessageProducer.sendMessage(jsonRequest);
            return new BaseResponse<>(200, "AIGC任务请求成功，结果稍后查看", "正在进行sql生成");
        } catch (Exception e) {
            log.error("请求失败");
            return new BaseResponse<>(500, "请求失败");
        }
    }

    // 获取用户AIGC诉求列表
    @PostMapping("/getUserQueryByUserIdWithPage")
    public BaseResponse<List<UserQuery>> getUserQueryByUserIdWithPage(@RequestBody GetUserQueryByUserIdWithPageReq getUserQueryByUserIdWithPageReq, HttpServletRequest request) throws Exception {
        long userId = getUserQueryByUserIdWithPageReq.getUserId();
        long cursor = getUserQueryByUserIdWithPageReq.getCursor();
        int num = getUserQueryByUserIdWithPageReq.getNum();
        if (num <= 0 ) num = 10;
        try {
            List<UserQuery> userQueryList = interactionService.getUserQueryByUserIdWithCursor(userId, cursor, num);
            return new BaseResponse<>(200, userQueryList, "查询成功");
        } catch (Exception e) {
            log.error("获取失败");
            return new BaseResponse<>(50001, Collections.emptyList(), "操作失败");
        }
    }
}
