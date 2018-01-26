package com.aem.community.core.models;

import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.cq.sightly.WCMUsePojo;
import com.aem.community.utils.LinkUtil;

public class PojoUtil extends WCMUsePojo {

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String getUrl() {
		String path = get("path", String.class);
		SlingHttpServletRequest request = get("req", SlingHttpServletRequest.class);
		return LinkUtil.getMappedUrl(path, request);
	}

}
