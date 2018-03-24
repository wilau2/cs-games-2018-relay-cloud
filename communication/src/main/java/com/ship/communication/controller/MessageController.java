package com.ship.communication.controller;

import com.ship.authorization.service.RankService;
import com.ship.authorization.service.UsersService;
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

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageRepository messageRequestRepository;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Message sendMessage(@RequestBody Message message, @CookieValue("SESSION") String cookie) {
        checkAccess(new ActionDto(message.getRecipient()), cookie);
        
        // si le rank du receiver est 2 au dessus de celui du sender, 
        // il faut que le message soit approuvé par les niveaux supérieurs
        if (messageRequiresApproval(message)){
            return messageRequestRepository.createMessageRequest(message);
        }

        // sinon, si le message est à envoyer à un level au-dessus (ou inférieur),
        // le message peut être envoyé directement
        if (messageCanBeSentDirectly(message)) {
            return messageRepository.save(message);
        }

        // sinon ben tu peux pas envoyer de message.
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public Resources<Resource<Message>> sendMessage() {
        return new Resources<>(StreamSupport.stream(messageRepository.findAll().spliterator(),false).map(MessageResource::toResource).collect(Collectors.toList()));
    }

    // route pour qu'un user puisse voir les messages requests qu'il doit approuver/refuser
    @RequestMapping(value = "/messageRequests", method = RequestMethod.GET)
    public Resources<Resource<Message>> sendMessageRequest() {
        return new Resources<>(StreamSupport.stream(messageRequestRepository.findAll().spliterator(),false).map(MessageResource::toResource).collect(Collectors.toList()));
    }

    // route pour permettre d'approuver/refuser une demande de message
    @RequestMapping(value = "/messageRequests", method = RequestMethod.POST)
    public Message sendMessage(@RequestBody String messageTitle, @RequestBody boolean isApproved, @CookieValue("SESSION") String cookie) {
        checkAccess(new ActionDto(message.getRecipient()), cookie);
        
        // on récupère le message dans le messageRepository
        Message message = new Resources<>(StreamSupport.stream(messageRepository.findByTitle(messageTitle).spliterator(),false);
        
        // j'ai inventé une propriété "isApproved" aux Messages
        message.isApproved = isApproved;
        
        // TODO: envoyer une notification si le message est approuvé
    }

    // route pour qu'un user puisse voir les logs des messages envoyés par les users inférieurs à son rôle
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public Resources<Resource<Message>> seeLogs() {
        // TODO: vérifier le ranking pour que seulement les messages des users ayant un ranking inférieur soient affichés
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

    // checks if the message can be sent based on the difference between the
    // sender's and the receiver's ranking levels
    private boolean checkRank(Message message, int levelDelta){
        String recipientRole = UsersService.loadUserRole(message.getRecipient());
        int recipientRank = RankService.getUserRank(recipientRole);
        String senderRole = UsersService.loadUserRole(message.getSender());
        int senderRank = RankService.getUserRank(senderRole);

        return recipientRank <= senderRank + level;
    }

    private boolean messageCanBeSentDirectly(Message message){
        return checkRank(message, 1);
    }

    private boolean messageRequiresApproval(Message message){
        return checkRank(message, 2);
    }

    
}
