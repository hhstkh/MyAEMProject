package com.aem.community.core.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
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
		
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		final Map<String, RequestParameter[]> params = request.getRequestParameterMap();
		
		for (final Map.Entry<String, RequestParameter[]> keyValue : params.entrySet()) {
			String key = keyValue.getKey();
			RequestParameter[] paraArr = keyValue.getValue();
			RequestParameter para = paraArr != null ? paraArr[0] : null;
			if (para != null) {
				InputStream inputStream = para.getInputStream();
				
				if (para.isFormField()) {
					out.println("Form field " + key + " with value " + Streams.asString(inputStream) + " detected.");
				} else {
					out.println("File field " + key + " with file name " + para.getFileName() + " detected.");
				}
			}
		}
		
	}
}
