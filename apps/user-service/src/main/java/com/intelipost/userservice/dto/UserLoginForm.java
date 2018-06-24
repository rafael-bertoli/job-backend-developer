package com.intelipost.userservice.dto;

import lombok.Getter;

/**
 * Form para o recebimento de dados de login de um usuário
 * @author Rafael
 */
@Getter
public class UserLoginForm {

    private String username;
    private String password;

    public UserLoginForm() {
    }

    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}
