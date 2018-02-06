package com.aem.community.core.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class NativeSoapServlet extends SlingAllMethodsServlet {
	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		// Whether to format the SOAP message response or not.
		String formatXML = request.getParameter("formatXML");

		String wsURL;
		// Get the endpoint
		if (!request.getParameter("endpoint").equals(""))
			wsURL = request.getParameter("endpoint");
		else
			throw new ServletException("Missing endpoint location!");

		// Get the SOAP message request
		String xmlInput = request.getParameter("soapmessage");
		String requestMessage = xmlInput;

		// Code to make a webservice HTTP request
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		String responseString = "";
		String outputString = "";
		// Optional: set your action
		// String SOAPAction =
		// "http://litwinconsulting.com/webservices/GetWeather";
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

		// Optional: set your action
		// httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		out = httpConn.getOutputStream();

		// write the content of the request to the outputstream of the HTTP
		// Connection.
		out.write(b);
		out.close();
		// ready with sending the request

		// Read the response.
		isr = new InputStreamReader(httpConn.getInputStream());
		in = new BufferedReader(isr);

		// Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString
					+ (formatXML == null ? "\n" : "");
		}

	}

	public String getWeather(String city) throws MalformedURLException,
			IOException {

		// Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL = "http://www.deeptraining.com/webservices/weather.asmx";
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://litwinconsulting.com/webservices/\">\n"
				+ " <soapenv:Header/>\n"
				+ " <soapenv:Body>\n"
				+ " <web:GetWeather>\n"
				+ " <!--Optional:-->\n"
				+ " <web:City>"
				+ city
				+ "</web:City>\n"
				+ " </web:GetWeather>\n"
				+ " </soapenv:Body>\n" + " </soapenv:Envelope>";

		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "http://litwinconsulting.com/webservices/GetWeather";
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		// Write the content of the request to the outputstream of the HTTP
		// Connection.
		out.write(b);
		out.close();
		// Ready with sending the request.

		// Read the response.
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		// Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}
		// Parse the String output to a org.w3c.dom.Document and be able to
		// reach every node with the org.w3c.dom API.
		Document document = parseXmlFile(outputString);
		NodeList nodeLst = document.getElementsByTagName("GetWeatherResult");
		String weatherResult = nodeLst.item(0).getTextContent();
		System.out.println("Weather: " + weatherResult);

		// Write the SOAP message formatted to the console.
		/*
		 * String formattedSOAPResponse = formatXML(outputString);
		 * System.out.println(formattedSOAPResponse);
		 */
		return weatherResult;
	}

	private Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
