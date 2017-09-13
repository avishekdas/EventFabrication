package com.ibm.maersk.rest.dao;

import java.util.List;

/**
 * 
 * @author ama
 * @see <a href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface EventDao {
	
	public List<EventEntity> getEvents(String orderByInsertionDate);

	public List<EventEntity> getRecentEvents(int numberOfDaysToLookBack);
	
	/**
	 * Returns a event given its id
	 * 
	 * @param id
	 * @return
	 */
	public EventEntity getEventById(Long id);
	
	/**
	 * Find event by feed
	 * 
	 * @param feed
	 * @return the event with the feed specified feed or null if not existent 
	 */
	public EventEntity getEventByEqptNo(String feed);	

	public void deleteEventById(Long id);

	public Long createEvent(EventEntity event);

	public void updateEvent(EventEntity event);

	/** removes all events */
	public void deleteEvents();

}
