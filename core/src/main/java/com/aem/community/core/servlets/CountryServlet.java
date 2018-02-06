package com.aem.community.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.community.webservice.country.CountriesPort;
import com.aem.community.webservice.country.CountriesPortService;
import com.aem.community.webservice.country.GetCountryRequest;
import com.aem.community.webservice.country.GetCountryResponse;
import com.google.gson.Gson;

@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Country Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths="+ "/bin/getCountry"
})
public class CountryServlet extends SlingAllMethodsServlet {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String countryName = request.getParameter("countryName");
		
		CountriesPortService countryWebService = new CountriesPortService();
		CountriesPort countriesPort = countryWebService.getCountriesPortSoap11();
		
		GetCountryRequest getCountryRequest = new GetCountryRequest();
		getCountryRequest.setName(countryName);
		
		GetCountryResponse countryResponse = countriesPort.getCountry(getCountryRequest);
		Gson gson = new Gson();
		String countryJson = gson.toJson(countryResponse);
		
		PrintWriter pw = response.getWriter();
		pw.write(countryJson);
	}
}
