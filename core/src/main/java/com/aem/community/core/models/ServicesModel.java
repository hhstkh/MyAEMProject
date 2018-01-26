package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.List;

import com.adobe.cq.sightly.WCMUsePojo;

public class ServicesModel extends WCMUsePojo {
	
	private List<ServiceItem> items = new ArrayList<ServiceItem>();

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public List<ServiceItem> getItems() {
		return items;
	}
}
