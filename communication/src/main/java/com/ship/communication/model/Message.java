package com.ship.communication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String sender;
    private static List<String> ranks = Arrays.asList("CREWMAN", "ENSIGN", "LIEUTENANT", "COMMANDER", "CAPTAIN", "VICE_ADMIRAL", "ADMIRAL");

    @JsonCreator
    public Message(@JsonProperty("id") Long id,
                   @JsonProperty("title") String title,
                   @JsonProperty("content") String content,
                   @JsonProperty("recipient") String recipient,
                   @JsonProperty("sender") String sender
                   ) {
        int senderRank = ranks.indexOf(sender);
        int recipientRank = ranks.indexOf(recipient);
        Assert.isTrue(recipientRank <= senderRank + 1, "Cannot send message");
        this.id = id;
        this.title = title;
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
