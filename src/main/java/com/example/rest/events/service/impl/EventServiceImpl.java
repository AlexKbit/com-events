package com.example.rest.events.service.impl;

import com.example.rest.events.dto.CmdCreateCollection;
import com.example.rest.events.model.Event;
import com.example.rest.events.repository.EventRepository;
import com.example.rest.events.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for events.
 */
@Service
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventRepository eventRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public String newCollection(CmdCreateCollection cmdCreateCollection) {
        String collectionId = cmdCreateCollection.getCollectionId();
        eventRepository.createCollection(collectionId);
        log.info("Create new collection {}", collectionId);
        return collectionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event addEvent(Event event, String collection) {
        Event newEvent = eventRepository.add(event, collection);
        log.info("Create new event with id {} for collection {}", newEvent.getId(), collection);
        return newEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirm(final String collectionId, final String eventId) {
        log.info("Confirm event {} from collection {}", eventId, collectionId);
        return eventRepository.delete(eventId, collectionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> findTop(final String collectionId, int count) {
        log.info("Load page with {} events for collection {}", count, collectionId);
        return eventRepository.findTop(count, collectionId);
    }
}
