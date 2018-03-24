package main.java.com.ship.communication.controller;

import com.ship.communication.model.Log;

@RestController
public class LogController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Log logRepository;

    @RequestMapping(value = "/logMessage", method = RequestMethod.POST)
    public Message logMessage(@RequestBody Log log, @CookieValue("SESSION") String cookie) {
        checkLog(log, "message");
        return logRepository.save(log);
    }

    //Todo: Create notifications
    @RequestMapping(value = "/logMessage", method = RequestMethod.POST)
    public Message logNotification(@RequestBody Log log, @CookieValue("SESSION") String cookie) {
        checkLog(log, "notification");
        return logRepository.save(log);
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public Resources<Resource<Log>> sendMessage() {
        return new Resources<>(StreamSupport.stream(logRepository.findAll().spliterator(),false).map(MessageResource::toResource).collect(Collectors.toList()));
    }

    private void checkLog(Log log, String type) {
        if (!log.getType().equals(type)) {
            //Todo: do something
        }
    }
}