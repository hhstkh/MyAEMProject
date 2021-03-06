package com.aem.community.webservice.country;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2018-02-05T17:53:10.134+07:00
 * Generated source version: 2.6.0
 * 
 */
@WebServiceClient(name = "CountriesPortService", 
                  wsdlLocation = "http://10.0.2.129:8080/MySpringboot/ws/countries.wsdl",
                  targetNamespace = "http://spring.io/guides/gs-producing-web-service") 
public class CountriesPortService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://spring.io/guides/gs-producing-web-service", "CountriesPortService");
    public final static QName CountriesPortSoap11 = new QName("http://spring.io/guides/gs-producing-web-service", "CountriesPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("http://10.0.2.129:8080/MySpringboot/ws/countries.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(CountriesPortService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://10.0.2.129:8080/MySpringboot/ws/countries.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public CountriesPortService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CountriesPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CountriesPortService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public CountriesPortService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public CountriesPortService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public CountriesPortService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns CountriesPort
     */
    @WebEndpoint(name = "CountriesPortSoap11")
    public CountriesPort getCountriesPortSoap11() {
        return super.getPort(CountriesPortSoap11, CountriesPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CountriesPort
     */
    @WebEndpoint(name = "CountriesPortSoap11")
    public CountriesPort getCountriesPortSoap11(WebServiceFeature... features) {
        return super.getPort(CountriesPortSoap11, CountriesPort.class, features);
    }

}
