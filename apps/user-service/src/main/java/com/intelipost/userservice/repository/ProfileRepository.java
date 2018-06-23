package com.intelipost.userservice.repository;

import com.intelipost.userservice.model.Profile;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositório de perfil de acesso.
 * <p>
 * Este repositório utiliza JdbcTemplate para realizar as consultas a base de
 * dados para melhoria de performance.
 * </p>
 *
 * @author Rafael
 */
@Repository
public class ProfileRepository {

    private JdbcTemplate jdbcTemplate;
    private PermissionRepository permissionRepository;

    @Autowired
    public ProfileRepository(JdbcTemplate jdbcTemplate, PermissionRepository permissionRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.permissionRepository = permissionRepository;
    }

    /**
     * Busca perfis de acesso de acordo com os filtros.
     *
     * @param name nome de perfil
     * @return os perfis correspondentes, caso não seja encontrado perfil
     * correspondente ao filtro retorna lista vazia
     */
    public List<Profile> findAllByFilter(String name) {
        List<Profile> result = jdbcTemplate.query("SELECT id, name, description FROM profiles where name like ?",
                (rs, rowNum) -> new Profile(rs.getString("id"), rs.getString("name"), rs.getString("description")),
                "%" + StringUtils.defaultIfBlank(name, "") + "%"
        );
        return result;
    }

    /**
     * Busca um perfil de acesso através do id.
     *
     * @param idProfile id do perfil de acesso
     * @return o perfil correspondete
     */
    public Optional<Profile> findById(String idProfile) {
        List<Profile> result = jdbcTemplate.query("SELECT id, name, description FROM profiles WHERE id = ?", (rs, i) -> {
            return new Profile(rs.getString("id"), rs.getString("name"), rs.getString("description"));
        }, idProfile);

        if (result.isEmpty()) return Optional.empty();
        
        Profile profile = result.get(0);
        profile.loadPermissions(permissionRepository);
        return Optional.of(profile);
    }

}
