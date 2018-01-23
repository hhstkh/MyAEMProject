use(function () {
	var items = [];
	var footNav = pageProperties.getInherited("footer-nav", "");
	var footConfigPage = pageManager.getPage(footNav);
	var it = footConfigPage.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());
	
	while(it.hasNext()) {
		var page = it.next();
		
		items.push({
			page:page
		})
	}
	
	return {
		items: items
	}
});