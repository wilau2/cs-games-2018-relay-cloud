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



    public static final int ROLE_ADMIRAL_AUTH = 6;
    public static final int ROLE_VICE_ADMIRAL_AUTH = 5;
    public static final int ROLE_CAPTAIN_AUTH = 4;
    public static final int ROLE_COMMANDER_AUTH = 3;
    public static final int ROLE_LIEUTENANT_AUTH = 2;
    public static final int ROLE_ENSIGN_AUTH = 1;
    public static final int ROLE_CREWMAN_AUTH = 0;

    private Map<String, String> users = new HashMap<>();
    private Map<String, Integer> userAuths = new HashMap<>();

    public UsersService() {
        users.put("admiral", ROLE_ADMIRAL);
        users.put("viceAdmiral", ROLE_VICE_ADMIRAL);
        users.put("captain", ROLE_CAPTAIN);
        users.put("commander", ROLE_COMMANDER);
        users.put("lieutenant", ROLE_LIEUTENANT);
        users.put("ensign", ROLE_ENSIGN);
        users.put("crewman", ROLE_CREWMAN);

        userAuths.put(ROLE_ADMIRAL, ROLE_ADMIRAL_AUTH);
        userAuths.put(ROLE_VICE_ADMIRAL, ROLE_VICE_ADMIRAL_AUTH);
        userAuths.put(ROLE_CAPTAIN, ROLE_CAPTAIN_AUTH);
        userAuths.put(ROLE_COMMANDER, ROLE_COMMANDER_AUTH);
        userAuths.put(ROLE_LIEUTENANT, ROLE_LIEUTENANT_AUTH);
        userAuths.put(ROLE_ENSIGN, ROLE_ENSIGN_AUTH);
        userAuths.put(ROLE_CREWMAN, ROLE_CREWMAN_AUTH);
    }

    public int loadUserAuth(String username) {
        return userAuths.get(username);
    }

    public String loadUserRole(String username) {
        return users.get(username);
    }
}