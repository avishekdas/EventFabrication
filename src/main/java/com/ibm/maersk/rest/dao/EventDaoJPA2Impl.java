package com.ibm.maersk.rest.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class EventDaoJPA2Impl implements EventDao {

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;
	
	public List<EventEntity> getEvents(String orderByInsertionDate) {
		String sqlString = null;
		if(orderByInsertionDate != null){
			sqlString = "SELECT p FROM EventEntity p" + " ORDER BY p.insertionDate " + orderByInsertionDate;
		} else {
			sqlString = "SELECT p FROM EventEntity p";
		}		 
		TypedQuery<EventEntity> query = entityManager.createQuery(sqlString, EventEntity.class);		

		return query.getResultList();
	}

	public List<EventEntity> getRecentEvents(int numberOfDaysToLookBack) {
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC+1"));//Munich time 
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -numberOfDaysToLookBack);//substract the number of days to look back 
		Date dateToLookBackAfter = calendar.getTime();
		
		String qlString = "SELECT p FROM EventEntity p where p.insertionDate > :dateToLookBackAfter ORDER BY p.insertionDate DESC";
		TypedQuery<EventEntity> query = entityManager.createQuery(qlString, EventEntity.class);		
		query.setParameter("dateToLookBackAfter", dateToLookBackAfter, TemporalType.DATE);

		return query.getResultList();
	}
	
	public EventEntity getEventById(Long id) {
		
		try {
			String qlString = "SELECT p FROM EventEntity p WHERE p.id = ?1";
			TypedQuery<EventEntity> query = entityManager.createQuery(qlString, EventEntity.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public EventEntity getEventByEqptNo(String feed) {
		
		try {
			String qlString = "SELECT p FROM EventEntity p WHERE p.feed = ?1";
			TypedQuery<EventEntity> query = entityManager.createQuery(qlString, EventEntity.class);		
			query.setParameter(1, feed);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	

	public void deleteEventById(Long id) {
		
		EventEntity event = entityManager.find(EventEntity.class, id);
		entityManager.remove(event);
		
	}

	public Long createEvent(EventEntity event) {
		Date date = new Date();
		event.setEvtTmst(new Timestamp(date.getTime()));
		entityManager.merge(event);
		entityManager.flush();//force insert to receive the id of the event
		
		return event.getEvntId();
	}

	public void updateEvent(EventEntity event) {
		//TODO think about partial update and full update 
		entityManager.merge(event);		
	}
	
	public void deleteEvents() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE events");		
		query.executeUpdate();
	}

}
