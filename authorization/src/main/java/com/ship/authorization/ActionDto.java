package com.ship.authorization;

public class ActionDto {
    private String recipient;

    public ActionDto() {
    }

    public ActionDto(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}

