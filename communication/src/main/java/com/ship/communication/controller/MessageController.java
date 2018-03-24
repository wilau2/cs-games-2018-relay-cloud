package com.ship.communication.controller;

import com.ship.communication.model.Message;
import com.ship.communication.model.resource.MessageResource;
import com.ship.communication.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class MessageController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageRepository messageRepository;

    public Message findByRecipientAndSender(String r, String s, MessageRepository mr){
        // stub gg find da message 
        return null;
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Message sendMessage(@RequestBody Message message, @CookieValue("SESSION") String cookie) {
        // log message
        log.info(message.getTitle().toUpperCase() + ": " + message.getContent());
        // allow the reply to a personal message
        if(findByRecipientAndSender(message.getRecipient(), message.getSender()) === null) {
            checkAccess(new ActionDto(message.getRecipient()), cookie);
        }
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

        String requestJson = "{\"recipient\":\"" + actionDto.getRecipient() + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", "SESSION=" + session);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        restTemplate.postForObject(url, entity, String.class);
    }

}
