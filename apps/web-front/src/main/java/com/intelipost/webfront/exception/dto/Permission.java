package com.intelipost.webfront.exception.dto;

import com.intelipost.webfront.exception.dto.enumeration.PermissionEnum;
import java.io.Serializable;
import lombok.Getter;

/**
 * Objecto de tranferencia para representar permissão.
 * @author Rafael
 */
@Getter
public class Permission implements Serializable, Comparable<Permission> {
    
    private String id;
    private PermissionEnum permission;

    public Permission() {
    }

    public Permission(String id, PermissionEnum permission) {
        this.id = id;
        this.permission = permission;
    }

    @Override
    public int compareTo(Permission o) {
        return this.permission.getOrder().compareTo(o.getPermission().getOrder());
    }
    
}
