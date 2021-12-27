package com.techpal.sn.dto;

import com.techpal.sn.models.Menu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class MenuDto implements Serializable {

    private String titreMenu;

    private String uidUser;

    private boolean isAuthorized;

    private String externalId;

    public MenuDto(Menu menu) {
        this.titreMenu = menu.getTitreMenu();
        this.uidUser = menu.getUser().getLinkedMeta().getExternalId();
        this.isAuthorized = menu.isAuthorized();
        this.externalId = menu.getExternalId().getExternalId();
    }

    public String getTitreMenu() {
        return titreMenu;
    }

    public void setTitreMenu(String titreMenu) {
        this.titreMenu = titreMenu;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public static MenuDto parse(Menu menu){
        return new MenuDto(menu);
    }

    public static List<MenuDto> parseAll(List<Menu> menus) {
        return menus.stream().map(MenuDto::parse).collect(Collectors.toList());
    }
}
