package com.ship.communication.model.resource;

import com.ship.communication.model.Message;
import org.springframework.hateoas.Resource;
import org.springframework.util.Assert;

public class MessageResource {

    public static Resource<Message> toResource(final Message message) {
        Assert.notNull(message, "message cannot be null.");

        return new Resource<Message> (message);
    }
}
