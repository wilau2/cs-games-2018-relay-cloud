package com.ship.authorization.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsersDeptService {
    public static final String DEPT_OPERATIONS = "DEPT_OPERATIONS";
    public static final String DEPT_MEDICAL = "DEPT_MEDICAL";
    public static final String DEPT_SCIENCE = "DEPT_SCIENCE";
    public static final String DEPT_COMMAND = "DEPT_COMMAND";

    private Map<String, String> users = new HashMap<>();

    public UsersDeptService() {
        users.put("operations", DEPT_OPERATIONS);
        users.put("medical", DEPT_MEDICAL);
        users.put("science", DEPT_SCIENCE);
        users.put("command", DEPT_COMMAND);
    }

    public String loadUserRole(String username) {
        return users.get(username);
    }
}