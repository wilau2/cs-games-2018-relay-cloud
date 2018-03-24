package com.ship.communication.model.resource;

import com.ship.communication.model.Message;
import org.springframework.hateoas.Resource;
import org.springframework.util.Assert;

public class LogResource {

    public static Resource<Log> toResource(final Log log) {
        Assert.notNull(log, "log cannot be null.");

        return new Resource<Log> (message);
    }
}
