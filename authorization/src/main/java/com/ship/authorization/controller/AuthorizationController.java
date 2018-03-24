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
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
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
            if (grantedAuthority.getAuthority().equals(ROLE_CREWMAN)) {
                if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL) ||recipientRole.contains(ROLE_CAPTAIN) ||recipientRole.contains(ROLE_COMMANDER) ||recipientRole.contains(ROLE_LIEUTENANT)) {
                    throw new ForbiddenAccessException();
                } 
            }
            else if (grantedAuthority.getAuthority().equals(ROLE_ENSIGN)) {
                if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL) ||recipientRole.contains(ROLE_CAPTAIN) ||recipientRole.contains(ROLE_COMMANDER)) {
                    throw new ForbiddenAccessException();
                } 
            }
            else if (grantedAuthority.getAuthority().equals(ROLE_LIEUTENANT)) {
                if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL) ||recipientRole.contains(ROLE_CAPTAIN)) {
                    throw new ForbiddenAccessException();
                } 
            }
            else if (grantedAuthority.getAuthority().equals(ROLE_COMMANDER)) {
                if (recipientRole.contains(ROLE_ADMIRAL) || recipientRole.contains(ROLE_VICE_ADMIRAL)) {
                    throw new ForbiddenAccessException();
                } 
            }
            else if (grantedAuthority.getAuthority().equals(ROLE_CAPTAIN)) {
                if (recipientRole.contains(ROLE_ADMIRAL)) {
                    throw new ForbiddenAccessException();
                } 
            }


            // TODO: Fix la logique ici, pour ce use case: Je comprend pas trop qui est quoi, mais ca l'air simple une fois
            // que c'est compris. Aussi je peux pas tester, les jvm font exploser mon orid. Jai du reboot 2 fois. RIP
            /**
             * As any crew member, if I send a message to someone +2 in rank, I need the authorization of
             * every levels of hierarchy between me and the recipient
             */
            int recipientPower = UsersService.getRolePower(grantedAuthority.getAuthority());
            int destinationPower = UsersService.getRolePower(grantedAuthority.getAuthority());

            // were trying to send something to someone higher in ranks by 2.
            // Make sure we have all the permissions between those 2 powers.
            if ((destinationPower - recipientPower) >= 2) {
                for (int i = recipientPower; i <= destinationPower; i++) {
                    String role = UsersService.getRoleByPower(i);
                    // if this permission is not granted, raise a ForbiddenAccessException.
                    if (!recipientRole.contains(role)) {
                        throw new ForbiddenAccessException();
                    }
                }
            }
        }

    }
}