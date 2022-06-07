package com.switchfully.evolveandgo.lmsbackend.security;

public record KeycloakUserDto (String userName, String password, Role role){
}
