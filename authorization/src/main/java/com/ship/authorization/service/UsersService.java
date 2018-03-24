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

    /**
     * Return an integer representing the power of a role (0 = god, 7 = scrub)
     * @param role
     * @return
     */
    public static int getRolePower(String role) {
        switch (role) {
            case ROLE_CREWMAN:
                return 6;
            case ROLE_ENSIGN:
                return 5;
            case ROLE_LIEUTENANT:
                return 4;
            case ROLE_COMMANDER:
                return 3;
            case ROLE_CAPTAIN:
                return 2;
            case ROLE_VICE_ADMIRAL:
                return 1;
            case ROLE_ADMIRAL:
                return 0;
            default:
                return -1;
        }
    }

    public static String getRoleByPower(int power) {
        switch (power) {
            case 6:
                return ROLE_CREWMAN;
            case 5:
                return ROLE_ENSIGN;
            case 4:
                return ROLE_LIEUTENANT;
            case 3:
                return ROLE_COMMANDER;
            case 2:
                return ROLE_CAPTAIN;
            case 1:
                return ROLE_VICE_ADMIRAL;
            case 0:
                return ROLE_ADMIRAL;
            default:
                return null;
        }
    }

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