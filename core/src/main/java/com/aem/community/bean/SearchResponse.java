package com.aem.community.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = Resource.class)
public class SearchResponse {
	
	@Inject
	@Optional
	@Named("jcr:content/jcr:title")
	private String title;
	
	@Inject
	@Optional
	@Named("jcr:content/jcr:description")
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
