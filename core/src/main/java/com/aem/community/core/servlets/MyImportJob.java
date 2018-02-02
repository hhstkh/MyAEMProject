package com.aem.community.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Import Job Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths="+ "/bin/myImportJob"
})
public class MyImportJob extends SlingAllMethodsServlet {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			final Map<String, RequestParameter[]> params = request.getRequestParameterMap();
			
			String path = "/content/AEM63App/admin";
			Node rootNode = null;
			for (final Map.Entry<String, RequestParameter[]> keyValue : params.entrySet()) {
				String key = keyValue.getKey();
				RequestParameter[] paraArr = keyValue.getValue();
				RequestParameter para = paraArr != null ? paraArr[0] : null;
				if (para != null) {
					InputStream inputStream = para.getInputStream();
					
					if (para.isFormField()) {
						if (para.getName() == "path") {
							path = para.getString();
						}
					} else {
						rootNode = request.getResourceResolver().resolve(request, path).adaptTo(Node.class);
						processFileToNode(inputStream, rootNode);
					}
				}
			}
		}
	}

	private void processFileToNode(InputStream inputStream, Node rootNode) {
		try {
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			
			String lineContent = "";
			while ((lineContent = bufferReader.readLine()) != null) {
				saveAdminNode(lineContent, rootNode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveAdminNode(String nodeName, Node rootNode) {
		Node n = null;
		try {
			if (rootNode.hasNode(nodeName)) {
				
			} else {
				n = rootNode.addNode(nodeName, "cq:Page");
				rootNode.getSession().save();
				
				Node contentNode = n.addNode("jcr:content", "cq:PageContent");
				n.getSession().save();

				contentNode.setProperty("cq:tempplate", "/apps/AEM63App/templates/admin");
				contentNode.setProperty("sling:resourceType", "AEM63App/components/structure/admin-page");
				contentNode.setProperty("jcr:title", nodeName);
				contentNode.getSession().save();
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
