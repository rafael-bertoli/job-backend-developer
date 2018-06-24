package com.intelipost.webfront.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Handler responsavel monitorar sessão de usuário
 *
 * @author Rafael
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserSession userSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (!userSession.isValid(request)) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
