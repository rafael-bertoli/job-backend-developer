package com.intelipost.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handler responsavel por aplicar a regra de basic authentication
 * @author Rafael
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    
    private static final Map<String, String> INVALID_CREDENTIALS = new HashMap<String, String>(){{ put("message", "Invalid credentials"); }};
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_AUTHORIZATION = "Basic";
    
    @Value("${spring.basic.auth.username}")
    private String username;
    @Value("${spring.basic.auth.password}")
    private String password;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(BASIC_AUTHORIZATION)) {
            String[] credential = new String(Base64.decodeBase64(authorization.substring(authorization.indexOf(" ")+1, authorization.length()))).split(":");
            if(credential.length > 1 && username.equals(credential[0]) && password.equals(credential[1])){
                return true;
            }
        }
        response.setStatus(Response.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(INVALID_CREDENTIALS));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
