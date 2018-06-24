/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelipost.webfront.session;

import com.intelipost.webfront.dto.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Classe de gerenciamento da sessão do usuário
 * @author Rafael
 */
@Component
public class UserSession {
    
    public boolean isValid(HttpServletRequest request){
        return request.getSession().getAttribute(SessionEnum.USER_SESSION.name()) != null;
    }
    
    public void startSession(HttpServletRequest request, User user){
        request.getSession().setAttribute(SessionEnum.USER_SESSION.name(), user);
    }
    
    public User getUserInSession(HttpServletRequest request){
        return (User)request.getSession().getAttribute(SessionEnum.USER_SESSION.name());
    }
    
    public void closeSession(HttpServletRequest request){
        request.getSession().removeAttribute(SessionEnum.USER_SESSION.name());;
    }
    
}
