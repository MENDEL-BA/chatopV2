package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.MenuGrantedDto;
import com.techpal.sn.models.Menu;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.User;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.MenuRepository;
import com.techpal.sn.security.services.MenuService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final UserDetailsServiceInfo userDetailsServiceInfo;

    private final MetaService metaService;

    public MenuServiceImpl(MenuRepository menuRepository, UserDetailsServiceInfo userDetailsServiceInfo, MetaService metaService) {
        this.menuRepository = menuRepository;
        this.userDetailsServiceInfo = userDetailsServiceInfo;
        this.metaService = metaService;
    }

    @Override
    public List<Menu> getMenuGrandtedByUser() {

        Optional<User> user = userDetailsServiceInfo.getUser();

        return menuRepository.findByUserAndIsAuthorizedIsTrue(user.get());
    }

    @Override
    public void revokeGrantedMenuForUser(MenuGrantedDto menuGrantedDto) {

        if (menuGrantedDto.getUidUser() == null) {
            new MessageResponse("L'utilisateur n'a pas été trouvé");
        }

        if (Objects.isNull(menuGrantedDto.getUidMenus()) || menuGrantedDto.getUidMenus().isEmpty()) {
            new MessageResponse("Veuillez choisir au moins un menu");
        }

        User user = userDetailsServiceInfo.getUserByExternalId(menuGrantedDto.getUidUser());

        if (user == null) {
            new MessageResponse("L'utilisateur n'a pas été trouvé");
        }
        Menu menuGranted = null;

        for (String externalIdMenu : menuGrantedDto.getUidMenus()) {
            Menu menu = getByExternalId(metaService.findByExternalId(externalIdMenu));

            menuGranted = revokeGranted(menu);

            menuGranted.setUser(user);

            menuRepository.saveAndFlush(menuGranted);
        }
    }

    @Override
    public Menu getByExternalId(Meta meta) {

        if (meta == null) {
            new MessageResponse("Veullez verifier un de vos paramtres");
        }

        return menuRepository.findByExternalId(meta);
    }

    @Override
    public Menu revokeGranted(Menu menu) {

        if (menu == null) {
            new MessageResponse("Le menu k'existe pas");
        }

        if (menu.isAuthorized() == true) {
            menu.setAuthorized(false);
        } else {
            menu.setAuthorized(true);
        }

        return menu;
    }
}
