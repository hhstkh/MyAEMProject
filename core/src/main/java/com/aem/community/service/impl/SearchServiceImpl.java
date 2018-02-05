package com.aem.community.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.community.service.SearchService;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

@Component(service = SearchServiceImpl.class)
public class SearchServiceImpl implements SearchService {
	
	@Reference
	private QueryBuilder builder;

	@Override
	public SearchResult getPageResults(String searchValue, String rootPath,
			SlingHttpServletRequest request) {
		Session session = request.getResourceResolver().adaptTo(Session.class);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", rootPath);
		map.put("type", "cq:Page");
		map.put("fulltext", searchValue);
        map.put("p.limit", "-1");
        map.put("orderby", "@jcr:content/cq:lastModified");
        map.put("orderby.sort", "desc");
        
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
	}

	@Override
	public SearchResult getPageResultsPerPage(String searchValue,
			String rootPath, int offset, int numberItemPerPage,
			SlingHttpServletRequest request) {
		Session session = request.getResourceResolver().adaptTo(Session.class);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", rootPath);
		map.put("type", "cq:Page");
		map.put("fulltext", searchValue);
        map.put("orderby", "@jcr:content/cq:lastModified");
        map.put("orderby.sort", "desc");
        
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        query.setStart(offset);
        query.setHitsPerPage(numberItemPerPage);
        return query.getResult();
	}

}
