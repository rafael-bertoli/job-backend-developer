package com.intelipost.userservice.model;

import java.io.Serializable;
import lombok.Getter;

/**
 * Modelo para representar permissão.
 * @author Rafael
 */
@Getter
public class Permission implements Serializable {
    
    private String id;
    private String permission;

    public Permission() {
    }

    public Permission(String id, String permission) {
        this.id = id;
        this.permission = permission;
    }
    
}
