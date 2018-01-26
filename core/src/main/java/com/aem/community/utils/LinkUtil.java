package com.aem.community.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

public final class LinkUtil {
	private static final String ANCHOR_CHARACTER = "#";
	
	public static String getMappedUrl(String path, SlingHttpServletRequest request) {
		if (StringUtils.isBlank(path)) {
			return ANCHOR_CHARACTER;
		}
		
		return request.getResourceResolver().map(request, path) + ".html";
		
	}
}
