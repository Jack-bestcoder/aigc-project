package com.yupi.springbootinit.websocket;



import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author buhao
 * @version MyInterceptor.java, v 0.1 2019-10-17 19:21 buhao
 */
@Component
public class MyInterceptor extends  HttpSessionHandshakeInterceptor {

    /**
     * 握手前
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 转换为ServletServerHttpRequest
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
        // 获取Session
        HttpSession session = httpServletRequest.getSession();
        System.out.println("准备握手");
        // 如果还在会话期内
        if (session != null) {
            String sessionId = session.getId();
            System.out.println("Session ID: " + sessionId);
            // 将Session ID放入WebSocket连接的属性中
            attributes.put("token", sessionId);
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        super.afterHandshake(request, response, wsHandler, exception);
        System.out.println("握手完成");
    }



}