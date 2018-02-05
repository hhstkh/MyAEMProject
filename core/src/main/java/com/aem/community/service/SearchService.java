package com.aem.community.service;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;

import com.aem.community.bean.SearchResponse;

public interface SearchService {
	public List<SearchResponse> getPageResults(String searchText, String searchInPath, int currentPage, int numberItemPerPage, SlingHttpServletRequest slingRequest);
}
