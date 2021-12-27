package com.techpal.sn.security.services;

import com.techpal.sn.dto.MenuDto;
import com.techpal.sn.dto.MenuGrantedDto;
import com.techpal.sn.models.Menu;
import com.techpal.sn.models.Meta;

import java.util.List;

public interface MenuService {

    List<Menu> getMenuGrandtedByUser();

    void revokeGrantedMenuForUser(MenuGrantedDto menuGrantedDto); //TODO: au lieu de la liste de menu plutot recevoir aussi la liste des menus avec les nvx authosrisations

    Menu getByExternalId(Meta meta);

    Menu revokeGranted(Menu menu);
}
