package com.switchfully.evolveandgo.lmsbackend;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsBackendApplication.class, args);
    }
    /**
     * This creates a client that allows you send requests to keycloak as an admin
     * <p>
     * TODO: Change the account used from master admin to admin of a specific client
     */
    @Bean
    public Keycloak keycloak(@Value("${master.keycloak.username}") String adminUsername, @Value("${master.keycloak.password}") String adminPassword) {
        return KeycloakBuilder.builder()
                .serverUrl("https://keycloak.switchfully.com/auth")
                .grantType(OAuth2Constants.PASSWORD)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                )
                .build();
    }

}
