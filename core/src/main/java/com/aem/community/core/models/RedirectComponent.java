package com.aem.community.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import com.adobe.cq.sightly.WCMUsePojo;
import com.aem.community.utils.LinkUtil;

public class RedirectComponent extends WCMUsePojo {
	private String url;
	
	@Override
	public void activate() throws Exception {
		SlingHttpServletRequest request = getRequest();
		String redirectTarget = getProperties().get("redirectTarget", String.class);
		
		if (StringUtils.isNotBlank(redirectTarget)) {
			url = LinkUtil.getMappedUrl(redirectTarget, request);
			
			SlingHttpServletResponse response = getResponse();
			response.setStatus(301);
			response.setHeader("Location", url);
			response.setHeader("Connection", "close");
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
