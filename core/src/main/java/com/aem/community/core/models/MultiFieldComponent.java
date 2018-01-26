package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.commons.json.JSONObject;

import com.adobe.cq.sightly.WCMUsePojo;

public class MultiFieldComponent extends WCMUsePojo {
	
	private List<MultifieldBean> submenuItems = new ArrayList<MultifieldBean>();
	
	@Override
	public void activate() throws Exception {
		JSONObject jsonObj;
		
		String[] itemsProps = getProperties().get("myUserSubmenu", String[].class);
		if (itemsProps != null) {
			for (String itemProp : itemsProps) {
				jsonObj = new JSONObject(itemProp);
				
				MultifieldBean multifieldBean = new MultifieldBean();
				multifieldBean.setTitle(jsonObj.getString("title"));
				multifieldBean.setLink(jsonObj.getString("link"));
				multifieldBean.setFlag(jsonObj.getString("flag"));
				
				submenuItems.add(multifieldBean);
			}
		}
	}

	public List<MultifieldBean> getSubmenuItems() {
		return submenuItems;
	}
}
