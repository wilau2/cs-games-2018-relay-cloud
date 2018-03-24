package com.ship.authorization.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RankService {

    public static final int ROLE_ADMIRAL = 6;
    public static final int ROLE_VICE_ADMIRAL = 5;
    public static final int ROLE_CAPTAIN = 4;
    public static final int ROLE_COMMANDER = 3;
    public static final int ROLE_LIEUTENANT = 2;
    public static final int ROLE_ENSIGN = 1;
    public static final int ROLE_CREWMAN = 0;

    private Map<String, Integer> ranks = new HashMap<>();

    public RankService() {
        ranks.put(UsersService.ROLE_ADMIRAL, ROLE_ADMIRAL);
        ranks.put(UsersService.ROLE_VICE_ADMIRAL, ROLE_VICE_ADMIRAL);
        ranks.put(UsersService.ROLE_CAPTAIN, ROLE_CAPTAIN);
        ranks.put(UsersService.ROLE_COMMANDER, ROLE_COMMANDER);
        ranks.put(UsersService.ROLE_LIEUTENANT, ROLE_LIEUTENANT);
        ranks.put(UsersService.ROLE_ENSIGN,ROLE_ENSIGN);
        ranks.put(UsersService.ROLE_CREWMAN, ROLE_CREWMAN);
    }

    public static int getUserRank(String userrole) {
        return ranks.get(userrole);
    }
}