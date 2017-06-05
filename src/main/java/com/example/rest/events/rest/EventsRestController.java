package com.example.rest.events.rest;

import com.example.rest.events.dto.CmdCreateCollection;
import com.example.rest.events.dto.CmdCreateEvent;
import com.example.rest.events.dto.EventDTO;
import com.example.rest.events.model.Event;
import com.example.rest.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for events-service.
 */
@RestController
@RequestMapping("collection")
public class EventsRestController extends RESTController<Event, EventDTO> {

    @Autowired
    EventService eventService;

    /**
     * Create new collection.
     */
    @PostMapping
    public ResponseEntity<?> postNewCollection(@RequestBody CmdCreateCollection cmdCreateCollection) {
        eventService.newCollection(cmdCreateCollection);
        return new ResponseEntity(cmdCreateCollection, HttpStatus.OK);
    }

    /**
     * Create new collection for users.
     */
    @PostMapping("add")
    public ResponseEntity<?> postNewEvent(@RequestBody CmdCreateEvent cmdCreateEvent) {
        Event newEvent = eventService.addEvent(mapToModel(cmdCreateEvent.getEvent()), cmdCreateEvent.getCollectionId());
        return new ResponseEntity(mapToDTO(newEvent), HttpStatus.OK);
    }

    /**
     * Gets events from collection.
     */
    @GetMapping("{collectionId}/events/{count}")
    public ResponseEntity<?> getEvents(@PathVariable String collectionId, @PathVariable int count) {
        List<Event> result = eventService.findTop(collectionId, count);
        return new ResponseEntity(mapToDTO(result), HttpStatus.OK);
    }

    /**
     * Confirm process for event.
     */
    @GetMapping("{collectionId}/event/{eventId}/confirmation")
    public ResponseEntity<?> putConfirm(@PathVariable String collectionId,
                                        @PathVariable String eventId) {
        boolean result = eventService.confirm(collectionId, eventId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
