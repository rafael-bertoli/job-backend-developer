package com.intelipost.userservice.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * Modelo para representar um perfil de acesso.
 * @author Rafael
 */
@Getter
public class Profile implements Serializable {
    
    private String id;
    private String name;
    private String description;
    private Set<Permission> permissions = new HashSet<>();

    public Profile() {
    }

    public Profile(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
}
