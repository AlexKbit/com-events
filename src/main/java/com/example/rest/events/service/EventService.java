package com.example.rest.events.service;

import com.example.rest.events.dto.CmdCreateCollection;
import com.example.rest.events.model.Event;

import java.util.List;

/**
 * Service of {@link Event}.
 */
public interface EventService {

    /**
     * Create new collection.
     * @param cmdCreateCollection command for create collection
     */
    String newCollection(CmdCreateCollection cmdCreateCollection);

    /**
     * Add new event.
     * @param event new event
     * @param collection collection for insert
     * @return new event
     */
    Event addEvent(Event event, String collection);

    /**
     * Confirm event in collection.
     * @param collectionId id of collection
     * @param eventId id of event
     * @return already exist or not
     */
    boolean confirm(String collectionId, String eventId);

    /**
     * Load page of event.
     * @param collectionId id of collection
     * @param count max count of events
     * @return list of events
     */
    List<Event> findTop(final String collectionId, int count);

}
