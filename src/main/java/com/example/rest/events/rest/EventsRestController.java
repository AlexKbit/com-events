package com.example.rest.events.rest;

import com.example.rest.events.dto.CmdCreateCollection;
import com.example.rest.events.dto.CmdCreateEvent;
import com.example.rest.events.dto.EventDTO;
import com.example.rest.events.model.Event;
import com.example.rest.events.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(basePath = "/collection", value = "event", description = "Operations with events", produces = "application/json")
public class EventsRestController extends RESTController<Event, EventDTO> {

    @Autowired
    EventService eventService;

    /**
     * Create new collection.
     */
    @PostMapping
    @ApiOperation(value = "Create new collection", nickname = "postNewCollection")
    public ResponseEntity<?> postNewCollection(@RequestBody CmdCreateCollection cmdCreateCollection) {
        eventService.newCollection(cmdCreateCollection);
        return new ResponseEntity(cmdCreateCollection, HttpStatus.OK);
    }

    /**
     * Create new collection for users.
     */
    @PostMapping("event")
    @ApiOperation(value = "Add new event", nickname = "postNewEvent")
    public ResponseEntity<?> postNewEvent(@RequestBody CmdCreateEvent cmdCreateEvent) {
        Event newEvent = eventService.addEvent(mapToModel(cmdCreateEvent.getEvent()), cmdCreateEvent.getCollectionId());
        return new ResponseEntity(mapToDTO(newEvent), HttpStatus.OK);
    }

    /**
     * Gets events from collection.
     */
    @GetMapping("{collectionId}/events/{count}")
    @ApiOperation(value = "Get events", nickname = "getEvents")
    public ResponseEntity<?> getEvents(@PathVariable String collectionId, @PathVariable int count) {
        List<Event> result = eventService.findTop(collectionId, count);
        return new ResponseEntity(mapToDTO(result), HttpStatus.OK);
    }

    /**
     * Confirm process for event.
     */
    @GetMapping("{collectionId}/event/{eventId}/confirmation")
    @ApiOperation(value = "Put confirm of event", nickname = "putConfirm")
    public ResponseEntity<?> putConfirm(@PathVariable String collectionId,
                                        @PathVariable String eventId) {
        boolean result = eventService.confirm(collectionId, eventId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
