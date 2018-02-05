package com.aem.community.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

@Component(service = Servlet.class, property = {
		Constants.SERVICE_DESCRIPTION + "=Search Query Builder Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/mySearchQueryBuilder" })
public class SearchQueryBuilderServlet extends SlingAllMethodsServlet {

	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private QueryBuilder builder;

	private Session session;

	@Override
	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, "jquerybuilder");

		ResourceResolver resolver = null;

		try {
			resolver = resolverFactory.getServiceResourceResolver(param);
			session = resolver.adaptTo(Session.class);

			String searchValue = request.getParameter("searchValue");
			String rootPath = request.getParameter("rootPath");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("path", rootPath);
			map.put("type", "cq:Page");
			map.put("fulltext", searchValue);
            map.put("p.limit", "-1");
            map.put("orderby", "@jcr:content/cq:lastModified");
            map.put("orderby.sort", "desc");
            
            Query query = builder.createQuery(PredicateGroup.create(map), session);
            SearchResult result = query.getResult();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
