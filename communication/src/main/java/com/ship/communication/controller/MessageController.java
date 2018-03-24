package com.ship.communication.controller;

import com.ship.communication.model.Message;
import com.ship.communication.model.resource.MessageResource;
import com.ship.communication.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class MessageController {
    const String admiral = "ADMIRAL";
    const String vice_admiral = "VICE_ADMIRAL";
    const String captain = "CAPTAIN";
    const String commander = "COMMANDER";
    const String lieutenant ="LIEUTENANT";
    const String ensign = "ENSIGN";
    const String crewman = "CREWMAN";
    const String[] ranks = {admiral, vice_admiral, captain, commander, lieutenant, ensign, crewman};
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Message sendMessage(@RequestBody Message message, @CookieValue("SESSION") String cookie) {
        checkAccess(new ActionDto(message.getRecipient()), cookie);
        return messageRepository.save(message);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public Resources<Resource<Message>> sendMessage() {
        return new Resources<>(StreamSupport.stream(messageRepository.findAll().spliterator(),false).map(MessageResource::toResource).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User: " + authentication.getName());
        System.out.println("User has authorities: " + userDetails.getAuthorities());
    }

    private void checkAccess(ActionDto actionDto, String session) {
        ServiceInstance service = discoveryClient.getInstances("authorization").get(0);
        String url = "http://" + service.getHost() + ":" + service.getPort() + "/" + "checkAccess";

        String sender = actionDto.getSender();
        if (RankDifference(sender, actionDto.getRecipient()) + 1 >= 0) {
            String requestJson = "{\"recipient\":\"" + actionDto.getRecipient() + "\"}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", "SESSION=" + session);

            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
            restTemplate.postForObject(url, entity, String.class);
            // TODO envoyer notif au recipient
        }
        else
        {
            // TODO Access denied
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
