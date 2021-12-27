package com.techpal.sn.dto;

import java.io.Serializable;
import java.util.List;

public class MenuGrantedDto implements Serializable {

    private String uidUser;

    private List<String>  uidMenus;

    public MenuGrantedDto(String uidUser, List<String> uidMenus) {
        this.uidUser = uidUser;
        this.uidMenus = uidMenus;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public List<String> getUidMenus() {
        return uidMenus;
    }

    public void setUidMenus(List<String> uidMenus) {
        this.uidMenus = uidMenus;
    }
}
