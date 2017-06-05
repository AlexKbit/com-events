package com.example.rest.events.rest;

import com.example.rest.events.app.ApplicationLauncher;
import com.example.rest.events.dto.CmdCreateCollection;
import com.example.rest.events.dto.CmdCreateEvent;
import com.example.rest.events.dto.EventDTO;
import com.example.rest.events.model.Event;
import com.example.rest.events.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Component Test for {@link EventsRestController}
 * MongoDB - embedded
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationLauncher.class})
public class EventsRestControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EventRepository eventRepository;

    @Before
    public void setup() throws Exception {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateCollection() throws Exception {
        CmdCreateCollection cmd = new CmdCreateCollection();
        cmd.setCollectionId(UUID.randomUUID().toString());
        mvc.perform(
                post("/collection")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateEvent() throws Exception {
        CmdCreateEvent cmd = new CmdCreateEvent();
        cmd.setCollectionId(UUID.randomUUID().toString());
        cmd.setEvent(generateEventDTO());
        mvc.perform(
                post("/collection/event")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());
    }

    @Test
    public void testConfirmEventExist() throws Exception {
        String collectionId = UUID.randomUUID().toString();
        List<Event> events = generateEvents(1);
        initEvents(events, collectionId);

        String queryHost = String.format("/collection/".concat("%s/event/%s/confirmation"),
                collectionId, events.get(0).getEventId());
        mvc.perform(
                get(queryHost)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testConfirmEventNotExist() throws Exception {
        String collectionId = UUID.randomUUID().toString();
        initEvents(Collections.EMPTY_LIST, collectionId);

        String queryHost = String.format("/collection/".concat("%s/event/%s/confirmation"),
                collectionId, UUID.randomUUID());
        mvc.perform(
                get(queryHost)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    public void testGetEvent() throws Exception {
        String collectionId = UUID.randomUUID().toString();
        List<Event> events = generateEvents(1);
        initEvents(events, collectionId);

        mvc.perform(
                get(String.format("/collection/%s/events/10", collectionId))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId", is(events.get(0).getEventId())))
                .andExpect(jsonPath("$[0].userId", is(events.get(0).getUserId())));
    }

    @Test
    public void testGetEventsEmpty() throws Exception {
        String collectionId = UUID.randomUUID().toString();
        initEvents(Collections.emptyList(), collectionId);

        mvc.perform(
                get(String.format("/collection/%s/events/10", collectionId))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(Collections.EMPTY_LIST)));
    }

    private void initEvents(List<Event> events, String collection) {
        eventRepository.createCollection(collection);
        events.forEach(event -> eventRepository.add(event, collection));
    }

    private List<Event> generateEvents(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateEvent()).collect(Collectors.toList());
    }

    private Event generateEvent() {
        Event event = new Event();
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(new Date());
        event.setExecuteTimestamp(new Date());
        event.setUserId(UUID.randomUUID().toString());
        event.setContent(UUID.randomUUID().toString());
        return event;
    }

    private EventDTO generateEventDTO() {
        EventDTO event = new EventDTO();
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(new Date());
        event.setExecuteTimestamp(new Date());
        event.setUserId(UUID.randomUUID().toString());
        event.setContent(UUID.randomUUID().toString());
        return event;
    }

}