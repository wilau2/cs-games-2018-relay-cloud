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

    const String admiral = "ADMIRAL";
    const String vice_admiral = "VICE_ADMIRAL";
    const String captain = "CAPTAIN";
    const String commander = "COMMANDER";
    const String lieutenant ="LIEUTENANT";
    const String ensign = "ENSIGN";
    const String crewman = "CREWMAN";
    const String[] ranks = {admiral, vice_admiral, captain, commander, lieutenant, ensign, crewman};

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

            if (RankDifference(grantedAuthority.getAuthority(), recipientRole) + 1 < 0) {
                throw new ForbiddenAccessException();
            }
            if (grantedAuthority.getAuthority().equals(ROLE_CREWMAN)) {
                if (recipientRole.contains(ROLE_ADMIRAL)) {
                    throw new ForbiddenAccessException();
                }
            }
        }
    }


    private int RankDifference(String rank1, String rank2)
    {
        int rank1Level = 0;
        int rank2Level = 0;
        for (int i = 0; i < ranks.length; ++i)
        {
            if (ranks[i] == rank1)
            {
                rank1Level = i;
            }
            if (ranks[i] == rank2)
            {
                rank2Level = i;
            }
        }
        return rank1Level - rank2Level;
    }
}