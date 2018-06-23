package com.intelipost.userservice.model;

import com.intelipost.userservice.repository.ProfileRepository;
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

    /**
     * Carrega dados do perfil do usuário.
     * @param profileRepository repositório de perfil de acesso {@link ProfileRepository}
     */
    public void loadProfile(ProfileRepository profileRepository){
        if(profileRepository == null) return;
        this.profile = profileRepository.findById(idProfile).orElse(null);
        //limpa o id do perfil pois o mesmo ja deverá existir dentro da objeto Perfil
        this.idProfile = null; 
    }
}
