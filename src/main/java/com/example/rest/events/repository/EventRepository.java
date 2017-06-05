package com.example.rest.events.repository;

import com.example.rest.events.model.Event;

import java.util.List;

/**
 * Repository for events.
 */
public interface EventRepository {

    /**
     * Create collection.
     * @param collection name of collection
     */
    void createCollection(String collection);

    /**
     * Add new event in collection.
     * @param event model of event
     * @param collection name of collection
     */
    Event add(Event event, String collection);
    /**
     * Find younger events.
     * @param count max count
     * @param collection name of collection
     * @return
     */
    List<Event> findTop(int count, String collection);

    /**
     * Remove event from collection.
     * @param eventId id of event
     * @param collection name of collection
     */
    boolean delete(final String eventId, final String collection);
}
