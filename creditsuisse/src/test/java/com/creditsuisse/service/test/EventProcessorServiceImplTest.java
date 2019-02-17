package com.creditsuisse.service.test;

import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.creditsuisse.dto.EventDTO;
import com.creditsuisse.models.EventEntity;
import com.creditsuisse.repository.EventRespository;
import com.creditsuisse.service.EventProcessorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class EventProcessorServiceImplTest {

	public static final String JSON_EVENT_START = "{\"id\":\"abcde\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495212}";
	public static final String JSON_EVENT_END = "{\"id\":\"abcde\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495217}";

	private EventDTO eventDtoStart;

	@InjectMocks
	private EventProcessorServiceImpl eventProcessorServiceImpl;

	@Mock
	private Logger mockLogger;
	@Mock
	private EventRespository mockEventRespository;

	private ConcurrentMap<String, EventDTO> events;

	@Before
	public void setUp() throws Exception {
		events = (ConcurrentMap<String, EventDTO>) ReflectionTestUtils.getField(eventProcessorServiceImpl, "events");
		eventDtoStart = new ObjectMapper().readValue(JSON_EVENT_START, EventDTO.class);
	}

	@Test
	public void processEvents_stateFinished_ok() throws Exception {
		eventProcessorServiceImpl.processEvents(JSON_EVENT_END);
		Assert.assertEquals(1, events.size());
		Assert.assertNotNull(events.get("abcde"));
	}

	@Test
	public void processEvents_stateStarted_ok() throws Exception {
		eventDtoStart.setEnd(1491377495217l);
		eventDtoStart.setStart(eventDtoStart.getTimestamp());
		events.put("abcde", eventDtoStart);
		eventProcessorServiceImpl.processEvents(JSON_EVENT_START);
		Assert.assertTrue("Did not remove event from inFlight", events.isEmpty());
		Mockito.verify(mockEventRespository, Mockito.times(1)).save(Mockito.any(EventEntity.class));
	}

	@Test
	public void createEventEntity() {
		eventDtoStart.setEnd(1491377495217l);
		eventDtoStart.setStart(eventDtoStart.getTimestamp());
		EventEntity actual = EventProcessorServiceImpl.createEventEntity(eventDtoStart);
		Assert.assertTrue(actual.getDuration() == 5l);
		Assert.assertTrue(actual.isAlert());
		Assert.assertEquals("12345", actual.getHost());
	}

}