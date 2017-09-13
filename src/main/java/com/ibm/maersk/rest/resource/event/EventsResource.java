package com.ibm.maersk.rest.resource.event;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.maersk.rest.errorhandling.AppException;
import com.ibm.maersk.rest.service.EventService;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author ibm
 * 
 */
@Component
@Path("/events")
public class EventsResource {

	@Autowired
	private EventService eventService;

	/*
	 * *********************************** CREATE
	 * ***********************************
	 */

	/**
	 * Adds a new resource (event) from the given json format (at least title and
	 * feed elements are required at the DB level)
	 * 
	 * @param event
	 * @return
	 * @throws AppException
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createEvent(Event event) throws AppException {
		Long createEventId = eventService.createEvent(event);
		return Response.status(Response.Status.CREATED)// 201
				.entity("A new event has been created")
				.header("Location", "http://localhost:8080/event-fabrication/events/" + String.valueOf(createEventId))
				.build();
	}

	/**
	 * Adds a new event (resource) from "form" (at least title and feed elements are
	 * required at the DB level)
	 * 
	 * @param title
	 * @param linkOnEventpedia
	 * @param feed
	 * @param description
	 * @return
	 * @throws AppException
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.TEXT_HTML })
	public Response createPodcastFromApplicationFormURLencoded(@FormParam("fqEventId") String fqEventId,
			@FormParam("source") String source, @FormParam("ptnrId") String ptnrId,
			@FormParam("msgType") String msgType, @FormParam("eqptNo") String eqptNo,
			@FormParam("shipNo") String shipNo, @FormParam("tpdocNo") String tpdocNo,
			@FormParam("actvtyLoc") String actvtyLoc, @FormParam("actvtyDate") String actvtyDate,
			@FormParam("actvtTime") String actvtTime) throws AppException {

		Date date = new Date();
		Timestamp evttmst = new Timestamp(date.getTime());
		Event event = new Event(3L, "P", evttmst, Long.parseLong(fqEventId), source, ptnrId, Long.parseLong(msgType),
				eqptNo, shipNo, tpdocNo, actvtyLoc, actvtyDate, actvtTime);
		Long createPodcastid = eventService.createEvent(event);

		return Response.status(Response.Status.CREATED)
				// 201
				.entity("A new podcast/resource has been created at /demo-rest-jersey-spring/podcasts/"
						+ createPodcastid)
				.header("Location",
						"http://localhost:8888/demo-rest-jersey-spring/podcasts/" + String.valueOf(createPodcastid))
				.build();
	}

	/**
	 * A list of resources (here events) provided in json format will be added to
	 * the database.
	 * 
	 * @param events
	 * @return
	 * @throws AppException
	 */
	@POST
	@Path("list")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createEvents(List<Event> events) throws AppException {
		eventService.createEvents(events);
		return Response.status(Response.Status.CREATED) // 201
				.entity("List of events was successfully created").build();
	}

	/*
	 * *********************************** READ ***********************************
	 */
	/**
	 * Returns all resources (events) from the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws AppException
	 */
	@GET
	// @Compress //can be used only if you want to SELECTIVELY enable compression at
	// the method level. By using the EncodingFilter everything is compressed now.
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Event> getEvents(@QueryParam("orderByInsertionDate") String orderByInsertionDate,
			@QueryParam("numberDaysToLookBack") Integer numberDaysToLookBack) throws IOException, AppException {
		List<Event> events = eventService.getEvents(orderByInsertionDate, numberDaysToLookBack);
		return events;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getEventById(@PathParam("id") Long id, @QueryParam("detailed") boolean detailed)
			throws IOException, AppException {
		Event eventById = eventService.getEventById(id);
		return Response.status(200)
				.entity(eventById, detailed ? new Annotation[] { EventDetailedView.Factory.get() } : new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header").allow("OPTIONS").build();
	}

	// @GET
	// @Path("{id}")
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// @EventDetailedView
	// public Event getEventById(@PathParam("id") Long id,
	// @QueryParam("detailed") boolean detailed)
	// throws IOException, AppException {
	// Event eventById = eventService.getEventById(id);
	//
	// return eventById;
	//// return Response.status(200)
	//// .entity(eventById, detailed ? new
	// Annotation[]{EventDetailedView.Factory.get()} : new Annotation[0])
	//// .header("Access-Control-Allow-Headers", "X-extra-header")
	//// .allow("OPTIONS").build();
	// }

	/*
	 * *********************************** UPDATE
	 * ***********************************
	 */

	/**
	 * The method offers both Creation and Update resource functionality. If there
	 * is no resource yet at the specified location, then a event creation is
	 * executed and if there is then the resource will be full updated.
	 * 
	 * @param id
	 * @param event
	 * @return
	 * @throws AppException
	 */
	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putEventById(@PathParam("id") Long id, Event event) throws AppException {

		Event eventById = eventService.verifyEventExistenceById(id);

		if (eventById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createEventId = eventService.createEvent(event);
			return Response.status(Response.Status.CREATED)
					// 201
					.entity("A new event has been created AT THE LOCATION you specified").header("Location",
							"http://localhost:8080/event-fabrication/events/" + String.valueOf(createEventId))
					.build();
		} else {
			// resource is existent and a full update should occur
			eventService.updateFullyEvent(event);
			return Response.status(Response.Status.OK)
					// 200
					.entity("The event you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location", "http://localhost:8080/event-fabrication/events/" + String.valueOf(id)).build();
		}
	}

	// PARTIAL update
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdateEvent(@PathParam("id") Long id, Event event) throws AppException {
		event.setEvntId(id);
		eventService.updatePartiallyEvent(event);
		return Response.status(Response.Status.OK)
				// 200
				.entity("The event you specified has been successfully updated").build();
	}

	/*
	 * *********************************** DELETE
	 * ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteEventById(@PathParam("id") Long id) {
		eventService.deleteEventById(id);
		return Response.status(Response.Status.NO_CONTENT)// 204
				.entity("Event successfully removed from database").build();
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteEvents() {
		eventService.deleteEvents();
		return Response.status(Response.Status.NO_CONTENT)// 204
				.entity("All events have been successfully removed").build();
	}

	public void seteventService(EventService eventService) {
		this.eventService = eventService;
	}

}
