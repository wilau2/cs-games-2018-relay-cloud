package com.ship.authorization.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsersService {

    private Map<String, String> users = new HashMap<>();

    public UsersService() {
        users.put("admiral", "ADMIRAL");
        users.put("viceAdmiral", "VICE_ADMIRAL");
        users.put("captain", "CAPTAIN");
        users.put("commander", "COMMANDER");
        users.put("lieutenant", "LIEUTENANT");
        users.put("ensign", "ENSIGN");
        users.put("crewman", "CREWMAN");
    }

    public String loadUserRole(String username) {
        return users.get(username);
    }
}