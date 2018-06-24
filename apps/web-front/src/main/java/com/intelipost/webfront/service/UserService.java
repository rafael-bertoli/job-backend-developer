package com.intelipost.webfront.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelipost.webfront.RestErrorHandler;
import com.intelipost.webfront.endpoints.UserEndpoint;
import com.intelipost.webfront.exception.UserNotFoundException;
import com.intelipost.webfront.exception.dto.User;
import com.intelipost.webfront.exception.dto.UserLoginForm;
import java.io.IOException;
import java.util.Collections;
import javax.naming.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Classe responsavel por integrar com o serviço de usuário.
 * @author Rafael
 */
@Service
public class UserService {

    @Autowired
    private LoadBalancerClient loadBalancer;
    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UserService(@Value("${spring.basic.auth.username}") final String username,@Value("${spring.basic.auth.password}") final String password) {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));
        this.restTemplate.setErrorHandler(new RestErrorHandler());
    }

    /**
     * Realiza a verificação de login de um usuário
     * @param username login
     * @param password senha
     * @return usuário encontrado
     * @throws UserNotFoundException usuário não encontrado
     * @throws ServiceUnavailableException Serviço indisponivel
     * @throws IOException 
     */
    public User doLogin(String username, String password) throws UserNotFoundException, ServiceUnavailableException, IOException {
        HttpEntity<UserLoginForm> requestBody = new HttpEntity<>(new UserLoginForm(username, password));
        ResponseEntity<String> response = restTemplate.exchange(getRequestURI(UserEndpoint.LOGIN_REQUEST), HttpMethod.POST, requestBody, String.class);
        checkResponse(response.getStatusCode());
        User user = mapper.readValue(response.getBody(), User.class);
        if(user.getProfile() != null) Collections.sort(user.getProfile().getPermissions());
        return user;
    }
    
    /**
     * Monta a URI a ser invocada, utilizando ribbon para fazer load balance.
     * @param endpoint Path do serviço
     * @return
     */
    private String getRequestURI(String endpoint){
        return loadBalancer.choose("user-service").getUri().toString()+endpoint;
    }
    
    /**
     * Valida o status de retorno da requisição.
     * @param status HTTP status
     * @throws UserNotFoundException Usuário não encontrado
     * @throws ServiceUnavailableException Serviço indisponivel
     */
    private void checkResponse(HttpStatus status) throws UserNotFoundException, ServiceUnavailableException{
        if(HttpStatus.NOT_FOUND.equals(status)){
            throw new UserNotFoundException();
        } else if(status.value() > 300){
            throw new ServiceUnavailableException();
        }
    }
}
