package com.intelipost.userservice.repository;

import com.intelipost.userservice.model.Permission;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositório de Permissões.
 * <p>
 * Este repositório utiliza JdbcTemplate para realizar as consultas a base de dados para melhoria de performance.
 * </p>
 * @author Rafael
 */
@Repository
public class PermissionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Busca permissões relacionadas com o id do perfil de acesso passado como parametro.
     * @param idProfile id do perfil de acesso
     * @return as permissões correspondentes, caso não exista permissão atrelada ao perfil retorna lista vazia
     */
    public List<Permission> findAllByProfile(String idProfile) {
        List<Permission> result = jdbcTemplate.query("SELECT p.id, p.permission FROM permissions p "
                + " JOIN profile_permissions pp ON pp.id_permission = p.id "
                + " WHERE pp.id_profile = ? ", (rs, rowNum) -> new Permission(rs.getString("id"), rs.getString("permission")),
                 idProfile);
        return result;
    }

}
