package com.yupi.springbootinit.controller;


import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.bizmq.BiMessageProducer;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.interaction.*;
import com.yupi.springbootinit.model.entity.UserQuery;
import com.yupi.springbootinit.service.InteractionService;
import com.yupi.springbootinit.service.SqlExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<Long> getSqlByPrompt(@RequestBody GetSqlByPromptRequest getSqlByPromptRequest, HttpServletRequest request) throws Exception {
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
            // 发消息异步执行
            String jsonRequest = JSONUtil.toJsonStr(sqlGeneration);
            biMessageProducer.sendMessage(jsonRequest);
            long id = userQuery.getId();
            return new BaseResponse<>(200, id, "AIGC任务请求成功，结果稍后查看");
        } catch (Exception e) {
            log.error("请求失败");
            log.error(e.getMessage());
            return new BaseResponse<>(500, null,"请求失败");
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
            log.info(e.getMessage());
            return new BaseResponse<>(ErrorCode.OPERATION_ERROR.getCode(), Collections.emptyList(), "操作失败");
        }
    }

    @PostMapping("/getUserQueryByPageNum")
    public BaseResponse<GetUserQueryByPageNumResp> getUserQueryByPageNum(@RequestBody GetUserQueryWithPageNum req, HttpServletRequest request) throws Exception {
        long userId = req.getUseId();
        int pageNum = req.getPageNum();
        int pageSize = req.getPageSize();
        if (pageSize <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        if (pageNum <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        if (userId <= 0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        List<UserQuery> userQueryList = interactionService.selectUserQueryWithPageNum(userId, (pageNum - 1) * pageSize, pageSize);
        int total = interactionService.countUserQueryByUserId(userId);
        return new BaseResponse<>(200, new GetUserQueryByPageNumResp(userQueryList, total), "查询成功");
    }


    @PostMapping("/getUserQueryById")
    public BaseResponse<UserQuery> getUserQueryById(@RequestBody GetUserQueryByIdReq req, HttpServletRequest request) throws Exception {
        try {
            if (req.getId() <= 0) return new BaseResponse<>(ErrorCode.PARAMS_ERROR.getCode(), null,"id不允许小于等于0");
            UserQuery userQuery = interactionService.getUserQueryById(req.getId());
            if (ObjectUtils.isEmpty(userQuery)) return new BaseResponse<>(ErrorCode.NOT_FOUND_ERROR.getCode(), null,"id不存在");
            return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), userQuery,"查询成功");
        } catch (Exception e) {
            log.error("查询失败, {}", request);
            log.info(e.getMessage());
            return new BaseResponse<>(ErrorCode.OPERATION_ERROR.getCode(),null, "操作失败");
        }
    }

    @PostMapping("/getBusinessTableCreateSql")
    public BaseResponse<String> getBusinessTableCreateSql(HttpServletRequest request) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), promptConstant.getBUSINESS_TABLE_STRUCTURE(),"查询成功");
    }
}
