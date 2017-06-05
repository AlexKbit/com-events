package com.example.rest.events.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.example.rest.events.repository.EventRepository;
import com.example.rest.events.model.Event;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository of {@link Event}.
 */
@Repository
@Transactional
public class EventRepositoryImpl implements EventRepository {

    private static final String FIELD_EVENT_ID = "eventId";
    private static final String FIELD_TIMESTAMP = "timestamp";
    private static final String FIELD_EXECUTE_TIMESTAMP = "executeTimestamp";

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCollection(String collection) {
        mongoTemplate.createCollection(collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event add(Event event, String collection) {
        mongoTemplate.insert(event, collection);
        return event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> findTop(int count, String collection) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, FIELD_TIMESTAMP),
                new Sort.Order(Sort.Direction.DESC, FIELD_EXECUTE_TIMESTAMP)));
        query.limit(count);
        return mongoTemplate.find(query, Event.class, collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final String eventId, final String collection) {
        return null != mongoTemplate.findAndRemove(
                new Query(Criteria.where(FIELD_EVENT_ID).is(eventId)),
                Event.class, collection);
    }
}
