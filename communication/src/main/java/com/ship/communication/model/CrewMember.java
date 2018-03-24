package com.ship.communication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

public class CrewMember implements Message {

    private int rank; 
    private String title; 

    class CrewMember(int rank){
        switch(rank){
            case 1: 
                this.title = "ADMIRAL";
            case 2: 
                this.title = "VICE_ADMIRAL";
            case 3:
                this.title = "CAPTAIN"; 
            case 4:
                this.title = "COMMANDER";
            case 5:
                this.title = "CREWMAN";
        }
    }

    public Message(CrewMember member String message) {
        if((this.rank + 1) > member.rank){
            System.out.println(message + "Message Sent"; )
        }
        else{
            System.out.println("Your Rank is to Low ");
        }
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
