package com.intelipost.webfront.dto;

import java.io.Serializable;
import lombok.Getter;

/**
 * Objecto de tranferencia para representar um usuário.
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

}
