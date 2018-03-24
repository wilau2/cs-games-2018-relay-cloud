package com.ship.communication.controller;

public class ActionDto {
    private String recipient;

    enum Rank {
        ADMIRAL(6), VICE_ADMIRAL(5), CAPTAIN(4), COMMANDER(3), LIEUTENANT(2), ENSIGN(1), CREWMAN(0);
        private int rank;

        private Rank(int rank) {
            this.rank = rank;
        }
    }

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
