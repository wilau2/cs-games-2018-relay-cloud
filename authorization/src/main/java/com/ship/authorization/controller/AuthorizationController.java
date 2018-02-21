package com.ship.authorization.controller;

import com.ship.authorization.ActionDto;
import com.ship.authorization.ForbiddenAccessException;
import com.ship.authorization.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.ship.authorization.service.UsersService.ROLE_ADMIRAL;
import static com.ship.authorization.service.UsersService.ROLE_CREWMAN;

@RestController
public class AuthorizationController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/checkAccess", method = RequestMethod.POST)
    public void checkAccess(Authentication authentication, @RequestBody ActionDto actionDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User: " + authentication.getName());
        System.out.println("User has authorities: " + userDetails.getAuthorities());
        System.out.println("Recipient: " + actionDto.getRecipient());
        String recipientRole = usersService.loadUserRole(actionDto.getRecipient());
        System.out.println("Role: " + recipientRole);

        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()){
            if (grantedAuthority.getAuthority().equals(ROLE_CREWMAN)) {
                if (recipientRole.contains(ROLE_ADMIRAL)) {
                    throw new ForbiddenAccessException();
                }
            }
        }
    }
}