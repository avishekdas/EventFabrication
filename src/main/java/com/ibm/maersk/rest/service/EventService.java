package com.ibm.maersk.rest.service;

import java.util.List;

import com.ibm.maersk.rest.errorhandling.AppException;
import com.ibm.maersk.rest.errorhandling.CustomReasonPhraseException;
import com.ibm.maersk.rest.resource.event.Event;

/**
 * 
 * @author ama
 * @see <a href=
 *      "http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface EventService {

	/*
	 * ******************** Create related methods **********************
	 */
	public Long createEvent(Event event) throws AppException;

	public void createEvents(List<Event> events) throws AppException;

	/*
	 ******************** Read related methods ********************
	 */
	/**
	 * 
	 * @param orderByInsertionDate
	 *            - if set, it represents the order by criteria (ASC or DESC) for
	 *            displaying events
	 * @param numberDaysToLookBack
	 *            - if set, it represents number of days to look back for events,
	 *            null
	 * @return list with events coressponding to search criterias
	 * @throws AppException
	 */
	public List<Event> getEvents(String orderByInsertionDate, Integer numberDaysToLookBack) throws AppException;

	/**
	 * Returns a event given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Event getEventById(Long id) throws AppException;

	/*
	 * ******************** Update related methods **********************
	 */
	public void updateFullyEvent(Event event) throws AppException;

	public void updatePartiallyEvent(Event event) throws AppException;

	/*
	 * ******************** Delete related methods **********************
	 */
	public void deleteEventById(Long id);

	/** removes all events */
	public void deleteEvents();

	/*
	 * ******************** Helper methods **********************
	 */
	public Event verifyEventExistenceById(Long id);

	/**
	 * Empty method generating a Business Exception
	 * 
	 * @throws CustomReasonPhraseException
	 */
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException;

}
