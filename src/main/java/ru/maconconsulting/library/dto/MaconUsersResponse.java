package ru.maconconsulting.library.dto;

import java.util.List;

public class MaconUsersResponse {

    private List<MaconUserDTO> maconUsers;

    public MaconUsersResponse(List<MaconUserDTO> maconUsers) {
        this.maconUsers = maconUsers;
    }

    public List<MaconUserDTO> getMaconUsers() {
        return maconUsers;
    }

    public void setMaconUsers(List<MaconUserDTO> maconUsers) {
        this.maconUsers = maconUsers;
    }
}
