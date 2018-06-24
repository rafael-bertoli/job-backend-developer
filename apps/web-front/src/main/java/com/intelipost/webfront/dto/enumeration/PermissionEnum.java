package com.intelipost.webfront.dto.enumeration;

/**
 * Enum de permissões
 * @author Rafael
 */
public enum PermissionEnum {
    
    USER("Perfil","/user"),
    PRODUCT("Produtos","#"),
    NFE("NF-e","#"),
    PURCHASE_ORDER("Ordem de Compra","#"),
    SALE("Venda","#");
    
    private String view;
    private String viewPath;

    private PermissionEnum(String view, String viewPath) {
        this.view = view;
        this.viewPath = viewPath;
    }

    public Integer getOrder() {
        return ordinal();
    }
    
    public String getView() {
        return view;
    }

    public String getViewPath() {
        return viewPath;
    }
    
}
