package com.techpal.sn.controllers;

import com.techpal.sn.dto.MenuDto;
import com.techpal.sn.dto.MenuGrantedDto;
import com.techpal.sn.dto.PatientDto;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.security.services.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MenuController {


    private final MenuService menuService;

    @GetMapping("/menus")
    public List<MenuDto> getMenuOfUserIsGranted() {
        return MenuDto.parseAll(menuService.getMenuGrandtedByUser());
    }

    @PutMapping("/revokeGrantedMenu")
    public void updatePatient(@RequestBody MenuGrantedDto menuGrantedDto) {

        if (menuGrantedDto == null) {
            new MessageResponse("Un des parametrer est null");
        }

        menuService.revokeGrantedMenuForUser(menuGrantedDto);
    }
}
