package com.intelipost.userservice.service;

import com.intelipost.userservice.exception.UserNotFoundException;
import com.intelipost.userservice.model.User;
import com.intelipost.userservice.repository.ProfileRepository;
import com.intelipost.userservice.repository.UserRepository;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de usuário
 * @author Rafael
 */
@Service
public class UserService {
    
    private UserRepository userRepository;
    private ProfileRepository profileRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }
    
    @Cacheable(value = "userLogin")
    @Transactional(readOnly = true)
    public User getByUsernameAndPassword(String username, String password) throws UserNotFoundException{
        if(StringUtils.isBlank(username)) throw new UserNotFoundException();
        User user = userRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new UserNotFoundException("User not found."));
        //Carrega perfil de acesso do usuário
        user.loadProfile(profileRepository);
        return user;
    }
    
    @Cacheable(value = "userDetailed")
    @Transactional(readOnly = true)
    public User getDetailedById(String idUser) throws UserNotFoundException {
        if(StringUtils.isBlank(idUser)) throw new UserNotFoundException();
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found."));
        //Carrega perfil de acesso do usuário
        user.loadProfile(profileRepository);
        return user;
    }
    
    @Cacheable(value = "users")
    @Transactional(readOnly = true)
    public List<User> getAllByFilter(String name) {
        return userRepository.findAllByFilter(name);
    }
    
}
