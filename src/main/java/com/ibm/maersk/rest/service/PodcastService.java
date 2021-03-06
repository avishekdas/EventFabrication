package com.ibm.maersk.rest.service;

import java.util.List;

import com.ibm.maersk.rest.errorhandling.AppException;
import com.ibm.maersk.rest.errorhandling.CustomReasonPhraseException;
import com.ibm.maersk.rest.resource.podcast.Podcast;

/**
 * 
 * @author ama
 * @see <a href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface PodcastService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	public Long createPodcast(Podcast podcast) throws AppException;
	public void createPodcasts(List<Podcast> podcasts) throws AppException;

		
	/*
	 ******************** Read related methods ********************
	  */ 	
	/**
	 * 
	 * @param orderByInsertionDate - if set, it represents the order by criteria (ASC or DESC) for displaying podcasts
	 * @param numberDaysToLookBack - if set, it represents number of days to look back for podcasts, null 
	 * @return list with podcasts coressponding to search criterias
	 * @throws AppException
	 */
	public List<Podcast> getPodcasts(String orderByInsertionDate, Integer numberDaysToLookBack) throws AppException;
	
	/**
	 * Returns a podcast given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public Podcast getPodcastById(Long id) throws AppException;
	
	
	
	/*
	 * ******************** Update related methods **********************
	 * */	
	public void updateFullyPodcast(Podcast podcast) throws AppException;
	public void updatePartiallyPodcast(Podcast podcast) throws AppException;	
	
		
	/*
	 * ******************** Delete related methods **********************
	 * */
	public void deletePodcastById(Long id);
	
	/** removes all podcasts */
	public void deletePodcasts();

	/*
	 * ******************** Helper methods **********************
	 * */
	public Podcast verifyPodcastExistenceById(Long id);
	
	/**
	 * Empty method generating a Business Exception
	 * @throws CustomReasonPhraseException
	 */
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException;

}
