package com.intelipost.userservice.controller;

import com.intelipost.userservice.dto.UserLoginForm;
import com.intelipost.userservice.exception.UserNotFoundException;
import com.intelipost.userservice.model.User;
import com.intelipost.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint de usuário
 * @author Rafael
 */
@RestController
@RequestMapping("/users")
@Api(value = "userService", description = "Operações referente a dados de usuários")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Listar usuários")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Invalid Credentials")
    }
    )
    @GetMapping(value = {"/"}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<User> users(@RequestParam(name="name",required = false) String name) {
        return userService.getAllByFilter(name);
    }
    
    @ApiOperation(value = "Busca usuário por id",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Invalid Credentials"),
            @ApiResponse(code = 404, message = "User not found.")
    }
    )
    @GetMapping(value = {"/{idUser}"}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public User getById(@PathVariable String idUser) throws UserNotFoundException {
        return userService.getDetailedById(idUser);
    }
    
    @ApiOperation(value = "Busca usuário por id", response = User.class, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Invalid Credentials"),
            @ApiResponse(code = 404, message = "User not found.")
    }
    )
    @PostMapping(value = {"/login"}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public User getByUsernameAndPassword(@RequestBody UserLoginForm form) throws UserNotFoundException {
        return userService.getByUsernameAndPassword(form.getUsername(), form.getPassword());
    }

}
