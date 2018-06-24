package com.intelipost.webfront.controller;

import com.intelipost.webfront.exception.UserNotFoundException;
import com.intelipost.webfront.exception.dto.User;
import com.intelipost.webfront.service.UserService;
import java.io.IOException;
import java.util.Map;
import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rafael
 */
@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password, RedirectAttributes redirectAttributes,  Map<String, Object> model) throws RestClientException, IOException {
        try {
            User user = userService.doLogin(username, password);
            request.getSession().setAttribute("USER_SESSION", user);
            return "redirect:/user";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("failure", "Login inválido, verifique seus dados.");
        } catch (ServiceUnavailableException e) {
            redirectAttributes.addFlashAttribute("failure", "O serviço de autenticação de usuário esta temporariamente indisponível. Tente novamente mais tarde.");
        } 
        return "redirect:/";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.getSession().removeAttribute("USER_SESSION");
        redirectAttributes.addFlashAttribute("success", "Você deslogou do sistema");
        return "redirect:/";
    }
    
    @RequestMapping("/user")
    public String user(HttpServletRequest request, Map<String, Object> model) {
        model.put("user", request.getSession().getAttribute("USER_SESSION"));
        return "user";
    }
}
