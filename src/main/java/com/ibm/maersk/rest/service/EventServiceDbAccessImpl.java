package com.ibm.maersk.rest.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.maersk.rest.dao.EventDao;
import com.ibm.maersk.rest.dao.EventEntity;
import com.ibm.maersk.rest.errorhandling.AppException;
import com.ibm.maersk.rest.errorhandling.CustomReasonPhraseException;
import com.ibm.maersk.rest.filters.AppConstants;
import com.ibm.maersk.rest.helpers.NullAwareBeanUtilsBean;
import com.ibm.maersk.rest.resource.event.Event;

public class EventServiceDbAccessImpl implements EventService {

	@Autowired
	EventDao eventDao;

	/*********************
	 * Create related methods implementation
	 ***********************/
	@Transactional("transactionManager")
	public Long createEvent(Event event) throws AppException {

		validateInputForCreation(event);

		// verify existence of resource in the db (feed must be unique)
		/*EventEntity eventByFeed = eventDao.getEventByEqptNo(event.getEqptNo());
		if (eventByFeed != null) {
			throw new AppException(Response.Status.CONFLICT.getStatusCode(), 409,
					"Event with feed already existing in the database with the id " + eventByFeed.getEvntId(),
					"Please verify that the feed and title are properly generated", AppConstants.BLOG_POST_URL);
		}*/
		
		return eventDao.createEvent(new EventEntity(event));
	}

	private void validateInputForCreation(Event event) throws AppException {
		if (event.getEqptNo() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400,
					"Provided data not sufficient for insertion",
					"Please verify that the feed is properly generated/set", AppConstants.BLOG_POST_URL);
		}
		if (event.getShipNo() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400,
					"Provided data not sufficient for insertion",
					"Please verify that the title is properly generated/set", AppConstants.BLOG_POST_URL);
		}
		// etc...
	}

	@Transactional("transactionManager")
	public void createEvents(List<Event> events) throws AppException {
		for (Event event : events) {
			createEvent(event);
		}
	}

	// ******************** Read related methods implementation
	// **********************
	public List<Event> getEvents(String orderByInsertionDate, Integer numberDaysToLookBack) throws AppException {

		// verify optional parameter numberDaysToLookBack first
		if (numberDaysToLookBack != null) {
			List<EventEntity> recentEvents = eventDao.getRecentEvents(numberDaysToLookBack);
			return getEventsFromEntities(recentEvents);
		}

		if (isOrderByInsertionDateParameterValid(orderByInsertionDate)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400,
					"Please set either ASC or DESC for the orderByInsertionDate parameter", null,
					AppConstants.BLOG_POST_URL);
		}
		List<EventEntity> events = eventDao.getEvents(orderByInsertionDate);

		return getEventsFromEntities(events);
	}

	private boolean isOrderByInsertionDateParameterValid(String orderByInsertionDate) {
		return orderByInsertionDate != null
				&& !("ASC".equalsIgnoreCase(orderByInsertionDate) || "DESC".equalsIgnoreCase(orderByInsertionDate));
	}

	public Event getEventById(Long id) throws AppException {
		EventEntity eventById = eventDao.getEventById(id);
		if (eventById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 404,
					"The event you requested with id " + id + " was not found in the database",
					"Verify the existence of the event with the id " + id + " in the database",
					AppConstants.BLOG_POST_URL);
		}

		return new Event(eventDao.getEventById(id));
	}

	private List<Event> getEventsFromEntities(List<EventEntity> eventEntities) {
		List<Event> response = new ArrayList<Event>();
		for (EventEntity eventEntity : eventEntities) {
			response.add(new Event(eventEntity));
		}

		return response;
	}

	public List<Event> getRecentEvents(int numberOfDaysToLookBack) {
		List<EventEntity> recentEvents = eventDao.getRecentEvents(numberOfDaysToLookBack);

		return getEventsFromEntities(recentEvents);
	}

	/*********************
	 * UPDATE-related methods implementation
	 ***********************/
	@Transactional("transactionManager")
	public void updateFullyEvent(Event event) throws AppException {
		// do a validation to verify FULL update with PUT
		if (isFullUpdate(event)) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400,
					"Please specify all properties for Full UPDATE",
					"required properties - id, title, feed, lnkOnEventpedia, description", AppConstants.BLOG_POST_URL);
		}

		Event verifyEventExistenceById = verifyEventExistenceById(event.getEvntId());
		if (verifyEventExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - " + event.getEvntId(),
					AppConstants.BLOG_POST_URL);
		}

		eventDao.updateEvent(new EventEntity(event));
	}

	/**
	 * Verifies the "completeness" of event resource sent over the wire
	 * 
	 * @param event
	 * @return
	 */
	private boolean isFullUpdate(Event event) {
		return event.getEvntId() == null || event.getEqptNo() == null || event.getShipNo() == null
				|| event.getActvtyLoc() == null || event.getTpdocNo() == null;
	}

	/*********************
	 * DELETE-related methods implementation
	 ***********************/
	@Transactional("transactionManager")
	public void deleteEventById(Long id) {
		eventDao.deleteEventById(id);
	}

	@Transactional("transactionManager")
	public void deleteEvents() {
		eventDao.deleteEvents();
	}

	public Event verifyEventExistenceById(Long id) {
		EventEntity eventById = eventDao.getEventById(id);
		if (eventById == null) {
			return null;
		} else {
			return new Event(eventById);
		}
	}

	@Transactional("transactionManager")
	public void updatePartiallyEvent(Event event) throws AppException {
		// do a validation to verify existence of the resource
		Event verifyEventExistenceById = verifyEventExistenceById(event.getEvntId());
		if (verifyEventExistenceById == null) {
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 404,
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - " + event.getEvntId(),
					AppConstants.BLOG_POST_URL);
		}
		copyPartialProperties(verifyEventExistenceById, event);
		eventDao.updateEvent(new EventEntity(verifyEventExistenceById));

	}

	private void copyPartialProperties(Event verifyEventExistenceById, Event event) {

		BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
		try {
			notNull.copyProperties(verifyEventExistenceById, event);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException {
		throw new CustomReasonPhraseException(4000, "message attached to the Custom Reason Phrase Exception");
	}

	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}

}
