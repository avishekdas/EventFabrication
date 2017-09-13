package com.ibm.maersk.rest.resource.podcast;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.maersk.rest.errorhandling.CustomReasonPhraseException;
import com.ibm.maersk.rest.service.PodcastService;

@Path("/mocked-custom-reason-phrase-exception")
public class CustomReasonPhraseExceptionMockResource {
	
	@Autowired
	private PodcastService podcastService;
	
	@GET
	public void testReasonChangedInResponse() throws CustomReasonPhraseException{
		podcastService.generateCustomReasonPhraseException();
	}
}
