package com.creditsuisse.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.creditsuisse.dto.EventDTO;
import com.creditsuisse.models.EventEntity;
import com.creditsuisse.repository.EventRespository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventProcessorServiceImpl {

	private final ConcurrentMap<String, EventDTO> events = new ConcurrentHashMap<>();

	@Autowired
	private EventRespository eventRespository;

	public void processEvents(String logLine) throws Exception {
		EventDTO event = new ObjectMapper().readValue(logLine, EventDTO.class);
		if (events.containsKey(event.getId())) {
			closeEvent(event, event.getId());
		} else {
			openEvent(event, event.getId());
		}
	}

	@Async
	public void closeEvent(EventDTO event, final String id) {

		EventDTO anEvent = events.get(id);
		if (anEvent.getEnd() == null) {
			anEvent.setEnd(event.getTimestamp());
		} else {
			anEvent.setStart(event.getTimestamp());
		}
		EventEntity entity = createEventEntity(anEvent);
		eventRespository.save(entity);
		events.remove(id);
		log.debug("Finishing event {} ", id);
	}

	public void openEvent(EventDTO event, String id) {
		if (event.getState().equals("STARTED")) {
			event.setStart(event.getTimestamp());
		} else {
			event.setEnd(event.getTimestamp());
		}
		events.put(id, event);
		log.debug("Opening event {}", id);
	}

	public static EventEntity createEventEntity(final EventDTO eventDTO) {
		EventEntity result = new EventEntity();

		result.setId(eventDTO.getId());

		result.setType(eventDTO.getType());
		result.setHost(eventDTO.getHost());
		result.setDuration(eventDTO.getEnd() - eventDTO.getStart());

		result.setAlert(result.getDuration() > 4);

		return result;
	}

	@PreDestroy
	public void destroy() throws Exception {
	}

}
