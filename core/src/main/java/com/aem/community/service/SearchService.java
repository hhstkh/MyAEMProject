package com.aem.community.service;

import org.apache.sling.api.SlingHttpServletRequest;

import com.day.cq.search.result.SearchResult;

public interface SearchService {
	public SearchResult getPageResults(String searchText, String searchInPath, SlingHttpServletRequest slingRequest);

	public SearchResult getPageResultsPerPage(String searchValue,
			String rootPath, int offset, int numberItemPerPage,
			SlingHttpServletRequest request);
}
