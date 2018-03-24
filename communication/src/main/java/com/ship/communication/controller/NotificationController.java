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

public class NotificationController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageRequestRepository messageRequestRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public Message approveMessageRequest(MessageRequest request) {
    	Notification notif = new Notification(request.id,
    			request.title,
    			"message approval : " + request.isApproved,
    			request.sender,
    			request.recipient);
    	notificationRepository.save(notif);
    }
    
}
