package com.aem.community.core.models;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.cq.sightly.WCMUsePojo;
import com.aem.community.bean.SearchResponse;
import com.aem.community.service.SearchService;

public class SearchComponent extends WCMUsePojo {
	
	private int numberItemPerPage = 10;
	
	private List<SearchResponse> searchResponses;

	@Override
	public void activate() throws Exception {
		SlingHttpServletRequest request = getRequest();
		ValueMap properties = getProperties();
		
		String rootPath = properties.get("rootPath", String.class);
		numberItemPerPage = properties.get("itemPerPage", Integer.class);
		String searchValue = request.getParameter("searchValue");
		String page = request.getParameter("page");
		int currentPage = 1;
		if (StringUtils.isNotBlank(page)) {
			currentPage = Integer.valueOf(page);
		}
		
		final SearchService searchService = getSlingScriptHelper().getService(SearchService.class);
		searchResponses = searchService.getPageResults(searchValue, rootPath, currentPage, numberItemPerPage, request);
		
	}

	public List<SearchResponse> getSearchResponses() {
		return searchResponses;
	}

	public void setSearchResponses(List<SearchResponse> searchResponses) {
		this.searchResponses = searchResponses;
	}
	
	
	

}
