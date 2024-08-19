package com.yupi.springbootinit.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;


@RestController
@RequestMapping("/socket")
@Slf4j
public class WebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);


    @GetMapping("/login")
    public String login(String userId, HttpServletRequest request) {
        HttpSession httpSesion = request.getSession();
        System.out.println("cookie中的JESESSIONID: " + request.getCookies()[1].getValue());
        System.out.println("request中的SESSIONID: " +  request.getSession().getId());
        LOGGER.info("创建新的httpSession={}", request.getSession().getId());
        httpSesion.setAttribute("name", "SpringBoot中文社区");
        return "访问成功";
    }
}
