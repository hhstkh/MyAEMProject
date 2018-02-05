package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.cq.sightly.WCMUsePojo;
import com.aem.community.bean.SearchResponse;
import com.aem.community.service.SearchService;
import com.aem.community.service.impl.SearchServiceImpl;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

public class SearchComponent extends WCMUsePojo {
	
	private boolean isDisplayPageNumner;
	private boolean isDisplayPre;
	private boolean isDispalyNext;
	private boolean noResult;
	private int totalPageNumber;
	private int numberItemPerPage = 10;
	private String pageUrl;
	
	private List<Integer> pageNumbers;
	private List<SearchResponse> searchResponses;

	@Override
	public void activate() throws Exception {
		SlingHttpServletRequest request = getRequest();
		ValueMap properties = getProperties();
		
		pageUrl = request.getResourceResolver().map(getCurrentPage().getPath()) + ".html";
		String rootPath = properties.get("rootPath", String.class);
		numberItemPerPage = properties.containsKey("itemPerPage") ? properties.get("itemPerPage", Integer.class) : 10;
		String searchValue = request.getParameter("searchValue");
		String page = request.getParameter("page");
		int currentPage = 1;
		if (StringUtils.isNotBlank(page)) {
			currentPage = Integer.valueOf(page);
		}
		
		final SearchService searchService = getSlingScriptHelper().getService(SearchServiceImpl.class);
		SearchResult searchResult = searchService.getPageResults(searchValue, rootPath, request);
		
		if (searchResult != null) {
			List<Hit> hits = searchResult.getHits();
			int totalItems = hits.size();
			if (totalItems > 0) {
				totalPageNumber = totalItems / numberItemPerPage;
				if (totalItems % numberItemPerPage != 0) {
					totalPageNumber += 1;
				}
				
				pageNumbers = new ArrayList<Integer>();
				int pageMaxNum = totalPageNumber + 1;
				for (int pageNum = 1; pageNum < pageMaxNum; pageNum++) {
					pageNumbers.add(pageNum);
				}
				
				if (pageNumbers != null && pageNumbers.size() > 1) {
					isDisplayPageNumner = true;
				}
				if (currentPage > 1) {
					isDisplayPre = true;
				}
				if (currentPage < totalPageNumber) {
					isDispalyNext = true;
				}
			}
		}
		
		int offset = (currentPage - 1) * numberItemPerPage;
		SearchResult searchResultPerPage = searchService.getPageResultsPerPage(searchValue, rootPath, offset, numberItemPerPage, request);
		convertToSearchResponse(searchResultPerPage, request);
		
		if (StringUtils.isNotBlank(searchValue) && CollectionUtils.isEmpty(pageNumbers)) {
			noResult = true;
		} else {
			noResult = false;
		}
	}

	private void convertToSearchResponse(SearchResult searchResultPerPage, SlingHttpServletRequest request) {
		if (searchResultPerPage != null) {
			List<Hit> hits = searchResultPerPage.getHits();
			searchResponses = new ArrayList<SearchResponse>();
			for (Hit hit : hits) {
				try {
					Resource resource = hit.getResource();
					if (resource != null) {
						/*SearchResponse searchResponse = new SearchResponse();
						searchResponse.setTitle(hit.getTitle());*/
						
						SearchResponse searchResponse = (SearchResponse) resource.adaptTo(SearchResponse.class);
						searchResponse.setHref(request.getResourceResolver().map(hit.getPath()) + ".html");
						searchResponses.add(searchResponse);
					}
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public List<SearchResponse> getSearchResponses() {
		return searchResponses;
	}

	public void setSearchResponses(List<SearchResponse> searchResponses) {
		this.searchResponses = searchResponses;
	}

	public boolean isDisplayPageNumner() {
		return isDisplayPageNumner;
	}

	public void setDisplayPageNumner(boolean isDisplayPageNumner) {
		this.isDisplayPageNumner = isDisplayPageNumner;
	}

	public boolean isDisplayPre() {
		return isDisplayPre;
	}

	public void setDisplayPre(boolean isDisplayPre) {
		this.isDisplayPre = isDisplayPre;
	}

	public boolean isDispalyNext() {
		return isDispalyNext;
	}

	public void setDispalyNext(boolean isDispalyNext) {
		this.isDispalyNext = isDispalyNext;
	}

	public boolean isNoResult() {
		return noResult;
	}

	public void setNoResult(boolean noResult) {
		this.noResult = noResult;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public List<Integer> getPageNumbers() {
		return pageNumbers;
	}

	public void setPageNumbers(List<Integer> pageNumbers) {
		this.pageNumbers = pageNumbers;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
