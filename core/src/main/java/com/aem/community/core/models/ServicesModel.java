package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServicesModel extends WCMUsePojo {
	
	private final Logger logger = LoggerFactory.getLogger(ServicesModel.class);
	
	private String serviceTitle;
	private List<ServiceItem> items = new ArrayList<ServiceItem>();

	@Override
	public void activate() throws Exception {
		serviceTitle = getProperties().get("title", String.class);
		String[] serviceListStr = getProperties().get("serviceList", String[].class);
		
		JsonParser parser = new JsonParser();
		int i = 0;
		if (serviceListStr != null) {
			for (String serviceItem : serviceListStr) {
				JsonObject jsonObj = (JsonObject)parser.parse(serviceItem);
				
				ServiceItem serviceObj = new ServiceItem();
				serviceObj.setTitle(jsonObj.get("title").getAsString());
				serviceObj.setDescription(jsonObj.get("description").getAsString());
				//serviceObj.setAvatarImgPath(jsonObj.get("avatarImg").getAsString());
				
				if (i % 3 == 0) {
					serviceObj.setNewRow(true);
				}
				
				items.add(serviceObj);
				
				i++;
			}
		}
		
	}

	public List<ServiceItem> getItems() {
		return items;
	}

	public String getServiceTitle() {
		return serviceTitle;
	}
}
