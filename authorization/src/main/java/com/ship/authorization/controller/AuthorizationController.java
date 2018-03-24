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
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.ship.authorization.service.UsersService.*;

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
            switch (grantedAuthority.getAuthority()) {
                case ROLE_CREWMAN:
                    if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL) || recipientRole.contains(ROLE_CAPTAIN)
                            || recipientRole.contains(ROLE_COMMANDER) || recipientRole.contains(ROLE_LIEUTENANT)) {
                        throw new ForbiddenAccessException();
                    }
                    break;
                case ROLE_ENSIGN:
                    if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL) || recipientRole.contains(ROLE_CAPTAIN)
                            || recipientRole.contains(ROLE_COMMANDER)) {
                        throw new ForbiddenAccessException();
                    }
                    break;
                case ROLE_LIEUTENANT:
                    if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL) || recipientRole.contains(ROLE_CAPTAIN)) {
                        throw new ForbiddenAccessException();
                    }
                    break;
                case ROLE_COMMANDER:
                    if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL)) {
                        throw new ForbiddenAccessException();
                    }
                    break;
                case ROLE_CAPTAIN:
                    if (recipientRole.contains(ROLE_ADMIRAL)) {
                        throw new ForbiddenAccessException();
                    }
                    break;
            }
        }
    }

    @RequestMapping(value = "/sendAndRequestAuth", method = RequestMethod.POST)
    public void sendAndRequestAuth(Authentication authentication, @RequestBody ActionDto actionDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User: " + authentication.getName());
        System.out.println("User has authorities: " + userDetails.getAuthorities());
        System.out.println("Recipient: " + actionDto.getRecipient());
        String recipientRole = usersService.loadUserRole(actionDto.getRecipient());
        System.out.println("Role: " + recipientRole);

        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()){
            String role = grantedAuthority.getAuthority();

            if (usersService.getUserRank(recipientRole) - usersService.getUserRank(role) == 2) {
                if (!requestAuth(authentication, actionDto)) {
                    throw new ForbiddenAccessException();
                }
            }
        }
    }

    @RequestMapping(value = "/requestAuth", method = RequestMethod.POST)
    public boolean requestAuth(Authentication authentication, @RequestBody ActionDto actionDto) {
        return actionDto.getAuthorization();
    }
}