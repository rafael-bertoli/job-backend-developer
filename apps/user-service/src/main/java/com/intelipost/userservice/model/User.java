package com.intelipost.userservice.model;

import java.io.Serializable;
import lombok.Getter;

/**
 * Modelo para representar um usuário.
 * @author Rafael
 */
@Getter
public class User implements Serializable {
    
    private String id;
    private String name;
    private String username;
    private String idProfile;
    private Profile profile;

    public User() {
    }

    public User(String id, String name, String username, String idProfile) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.idProfile = idProfile;
    }

}
