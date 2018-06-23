package com.intelipost.userservice.repository;

import com.intelipost.userservice.model.User;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositório de usuário.
 * <p>
 * Este repositório utiliza JdbcTemplate para realizar as consultas a base de dados para melhoria de performance.
 * </p>
 * @author Rafael
 */
@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Busca usuários de acordo com os filtros.
     * @param name nome de usuário
     * @return os usuários correspondetes, caso não seja encontrado usuários retorna lista vazia
     */
    public List<User> findAllByFilter(String name) {
        List<User> result = jdbcTemplate.query("SELECT id, name, username, id_profile FROM users WHERE name like ?", 
                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("username"), rs.getString("id_profile")), 
                "%"+StringUtils.defaultIfBlank(name, "")+"%"
        );
        return result;
    }

    /**
     * Busca um usuário através do id.
     * @param idUser id do usuário
     * @return o usuário correspondente
     */
    public Optional<User> findById(String idUser) {
        List<User> result = jdbcTemplate.query("SELECT id, name, username, id_profile FROM users WHERE id = ?", 
                (rs, i) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("username"), rs.getString("id_profile"))
                , idUser);
        
        if(!result.isEmpty()) return Optional.of(result.get(0));
        
        return Optional.empty();
    }
    
    /**
     * Busca um usuário através de login e senha.
     * @param username login do usuário
     * @param password senha
     * @return o usuário correspondente
     */
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        List<User> result = jdbcTemplate.query("SELECT id, name, username, id_profile FROM users WHERE username = ? AND password = ?",  
                (rs, i) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("username"), rs.getString("id_profile"))
                , username, password);
        
        if(!result.isEmpty()) return Optional.of(result.get(0));
        
        return Optional.empty();
    }

}
