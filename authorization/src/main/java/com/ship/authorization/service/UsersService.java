package com.ship.authorization.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsersService {

    public static final String ROLE_ADMIRAL = "ROLE_ADMIRAL";
    public static final String ROLE_VICE_ADMIRAL = "ROLE_VICE_ADMIRAL";
    public static final String ROLE_CAPTAIN = "ROLE_CAPTAIN";
    public static final String ROLE_COMMANDER = "ROLE_COMMANDER";
    public static final String ROLE_LIEUTENANT = "ROLE_LIEUTENANT";
    public static final String ROLE_ENSIGN = "ROLE_ENSIGN";
    public static final String ROLE_CREWMAN = "ROLE_CREWMAN";

    private Map<String, String> users = new HashMap<>();

    public UsersService() {
        users.put("admiral", ROLE_ADMIRAL);
        users.put("viceAdmiral", ROLE_VICE_ADMIRAL);
        users.put("captain", ROLE_CAPTAIN);
        users.put("commander", ROLE_COMMANDER);
        users.put("lieutenant", ROLE_LIEUTENANT);
        users.put("ensign", ROLE_ENSIGN);
        users.put("crewman", ROLE_CREWMAN);
    }

    public String loadUserRole(String username) {
        return users.get(username);
    }
}