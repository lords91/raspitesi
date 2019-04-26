package com.unisa.raspitesi.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EventPublisherService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final Logger log = LoggerFactory.getLogger(EventPublisherService.class);
    public static EventPublisherService eventPublisherService = null;

    @Autowired
    public EventPublisherService() {

    }

    @PostConstruct
    public void postConstruct() {
        log.info("EventPublisherService Initialized Correctly.");
        eventPublisherService = this;
    }

    public void publishEvent(Object object) {
        applicationEventPublisher.publishEvent(object);
    }
}
