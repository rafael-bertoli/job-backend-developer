package com.intelipost.webfront.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Objecto de tranferencia para representar um perfil de acesso.
 * @author Rafael
 */
@Getter
public class Profile implements Serializable {
    
    private String id;
    private String name;
    private String description;
    private List<Permission> permissions = new ArrayList<>();

    public Profile() {
    }

}
