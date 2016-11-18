package introsde.rest.ehealth.client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TestClient{
	
	private static String first_id;
	private static String last_id;
	private static DocumentBuilderFactory domFactory;
	private static DocumentBuilder builder;
	private static XPath xpath;
	private static WebTarget service;
	private static String start;
	private static String request;
	private static String type;
	private static String content;
	private static boolean result;
	private static String xml;
	private static String json;
	private static Document doc;
	private static Response resp;
	private static String measure_id;
	private static String measure_type;
	private static ObjectMapper mapper;
	
	private static void initialize() throws ParserConfigurationException{
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		service = client.target(getBaseURI());
		System.out.println("Test started at url:"+ getBaseURI());
		domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true);
	    builder = domFactory.newDocumentBuilder();
	    XPathFactory factory = XPathFactory.newInstance();
	    xpath = factory.newXPath();
	    
	    
	    mapper = new ObjectMapper();
	}
	
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException {
		initialize();
		
		// request with accept: APPLICATION_XML
//		request1XML();
//		printResult();
//		request2XML(first_id);
//		printResult();
//		request3XML();
//		printResult();
//		String newperson_id = request4XML();
//		printResult();
//		request5XML(newperson_id);
//		printResult();
//		String[] s = request6XML();
//		printResult();
//	    request7XML(s, first_id);
//	    String id = first_id;
//	    if (result == false){
//	    	request7XML(s, last_id);
//	    	id = last_id;
//	    }
//	    printResult();
//	    request8XML(id);
//	    printResult();
//	    request9XML(s);
//	    printResult();
	    
		request1JSON();
	    

	    
	    
	    
		
	    
	    

	}

	private static URI getBaseURI() {
		//return UriBuilder.fromUri("http://10.21.158.56:5700/assignment").build();
		return UriBuilder.fromUri("https://introsde2016-assignment2.herokuapp.com/assignment").build();
	}
	
	
	
	private static void printResult() throws TransformerException{
		System.out.print(start + request + " Accept: " + type);
		if (content != null)
			System.out.println(" Content-Type:  " + content);
		else 
			System.out.println();
		if (result)
			System.out.println("=> Result: OK");
		else 
			System.out.println("=> Result: ERROR");
		if (resp != null){
			System.out.println("=> HTTP Status: " + resp.getStatus());
			System.out.println(printXML(doc));
		}else{
			System.out.println("=> HTTP Status: NO RESPONSE");
		}
		
	}
	

	
	
	private static String printXML(Document doc) throws TransformerException{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	    //initialize StreamResult with File object to save to file
	    StreamResult result1 = new StreamResult(new StringWriter());
	    DOMSource source = new DOMSource(doc);
	    transformer.transform(source, result1);
	    String xmlString = result1.getWriter().toString();
	    return xmlString;
	}
	
	
	private static void request1XML() throws SAXException, IOException, XPathExpressionException, TransformerException{
		// GET Request #1 --- GET  BASEURL/person
	    // Accept: application/xml
	    //variable
	    start = "Request #1: GET /";
	    request = "person";
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    result = false;
	    
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    XPathExpression expr = xpath.compile("//*");
	    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	    
	    if (nodes.getLength() > 2) result = true;
	    
	    // first id
	    expr = xpath.compile("//person[1]/idPerson");
	    Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    first_id = node.getTextContent();
	    
	    // last id
	    expr = xpath.compile("//person[last()]/idPerson");
	    node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    last_id = node.getTextContent();
		
	}
	
	
	private static void request2XML(String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // GET Request #2 --- GET  BASEURL/person/first_id
	    // Accept: application/xml
	    //variable
	    start = "Request #2: GET /";
	    request = "person/"+id;
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    result = false;
	    
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    if (!xml.isEmpty())
	    	doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    if (resp.getStatus() == 200 || resp.getStatus() == 202) result = true;
	    
		
	}
	
	private static void request3XML() throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // PUT Request #3 --- PUT  BASEURL/person/first_id
	    // Accept: application/xml
	    //variable
	    start = "Request #3: PUT /";
	    request = "person/"+first_id;
	    type = MediaType.APPLICATION_XML;
	    content = MediaType.APPLICATION_XML;
	    result = false;
	    
	    String newName = "Jon";
	    String requestBody = "<person><name>"+ newName +"</name></person>";
	    resp = service.path(request).request().accept(type).put(Entity.entity(requestBody, content));
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));
    
	    XPathExpression expr = xpath.compile("//name");
	    String firstname = (String) expr.evaluate(doc, XPathConstants.STRING);
	    if (newName.equals(firstname)) result = true;
	    
	}
	
	
	
	private static String request4XML() throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// POST Request #4 --- POST  BASEURL/person
	    // Accept: application/xml
	    //variable
	    start = "Request #4: POST /";
	    request = "person";
	    type = MediaType.APPLICATION_XML;
	    content = MediaType.APPLICATION_XML;
	    result = false;
	    String newperson_id = "";
	    
	    String requestBody = "<person>"
	    		+ "<name>Chuck</name>"
	    		+ "<lastname>Norris</lastname>"
	    		+ "<birthdate>01/01/1945</birthdate>"
	    		+ "<healthProfile>"
	    		+ "<measureType><measureDef><idMeasureDef>1</idMeasureDef></measureDef><value>78.9</value></measureType>"
	    		+ "<measureType><measureDef><idMeasureDef>2</idMeasureDef></measureDef><value>172</value></measureType>"
	    		+ "</healthProfile></person>";


	    resp = service.path(request).request().accept(type).post(Entity.entity(requestBody, content));
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));
	    
	    XPathExpression expr = xpath.compile("//idPerson");
	    newperson_id = (String) expr.evaluate(doc, XPathConstants.STRING);
	    if ((resp.getStatus() == 200 || resp.getStatus() == 201 || resp.getStatus() == 202) && ! newperson_id.isEmpty()) result = true;
	    return newperson_id;
	    
	}
	
	
	private static void request5XML(String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// DELETE Request #5 --- DELETE  BASEURL/person/id
	    // Accept: application/xml
	    // variable
	    start = "Request #5: DELETE /";
	    request = "person/"+id;
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    doc = null;
	    

	    Response this_resp = service.path(request).request().accept(type).delete();
	    request2XML(id);
	    
	    // reset variable
	    start = "Request #5: DELETE /";
	    request = "person/"+id;
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    result = false;
	    
	    if (resp.getStatus()==404) result = true;
	    resp = this_resp;
	}
	
	private static String[] request6XML() throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// GET Request #6 --- GET  BASEURL/measureTypes
	    // Accept: application/xml
	    //variable
	    start = "Request #6: GET /";
	    request = "measureTypes";
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    result = false;
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    XPathExpression expr = xpath.compile("//*");
	    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	    
	    if (nodes.getLength() > 2) result = true;
	    String[] measureTypes= new String[nodes.getLength()-1];
 	    for (int i = 1; i< nodes.getLength(); i++){
	    	measureTypes[i-1]=nodes.item(i).getTextContent();
	    }
 	    return measureTypes;
	    
	}
	
	private static void request7XML(String[] vector, String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// GET Request #7 --- GET  BASEURL/person/{id}/{measureType}
	    // Accept: application/xml
	    //variable
	    start = "Request #7: GET /";
	    request = "person/"+ id + "/";
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    result = false;
	    Response this_res = null;
	    String this_req = null;
	    
	    for (int i = 0; i<vector.length; i++){
	    	String request1 = request + vector[i];
	    	
	    	resp = service.path(request1).request().accept(type).get();
	    	if(resp.getStatus() == 200){
	    		result = true;
	    		xml = resp.readEntity(String.class);
	    	    doc = builder.parse(new InputSource(new StringReader(xml)));	    	    
	    	    XPathExpression expr = xpath.compile("//healthMeasureHistory[1]/measureName");
	    	    Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    		measure_type = node.getTextContent();
	    		expr = xpath.compile("//healthMeasureHistory[1]/idMeasureHistory");
	    	    node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    		measure_id = node.getTextContent();
	    		id = first_id;
	    		this_res = resp;
	    		this_req = request1;
	    		
	    	}
	    	
	    }
	    
	    resp = this_res;
	    request = this_req;
	    
	}
	
	private static void request8XML(String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // GET Request #8 --- GET  BASEURL/person/{id}/{measureType}/{mid}
	    // Accept: application/xml
	    //variable
	    start = "Request #8: GET /";
	    request = "person/" + id + "/" + measure_type + "/" + measure_id;
	    type = MediaType.APPLICATION_XML;
	    content = null;
	    result = false;
	    
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    if (!xml.isEmpty()) 
	    	doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    if (resp.getStatus() == 200 || resp.getStatus() == 202) result = true;  
		
	}
	
	
	private static void request9XML(String[] vector) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // GET Request #9 --- POST  BASEURL/person/{first_person_id}/{measureType}
	    // Accept: application/xml
	    
	    
	    request7XML(vector, first_id);
	    XPathExpression expr = xpath.compile("count(//healthMeasureHistory)");
	    int count = Integer.parseInt((String)expr.evaluate(doc, XPathConstants.STRING));
	    
	    //variable
	    start = "Request #9: POST /";
	    type = MediaType.APPLICATION_XML;
	    content = MediaType.APPLICATION_XML;
	    result = false;
	    request = "person/" + first_id + "/" + measure_type;
	    String requestBody = "<healthMeasureHistory><value>72</value><created>09/12/2011</created></healthMeasureHistory>";


	    Response this_resp = service.path(request).request().accept(type).post(Entity.entity(requestBody, content));
	    String this_xml = this_resp.readEntity(String.class);
	    Document this_doc = builder.parse(new InputSource(new StringReader(this_xml)));
	    
	    request7XML(vector, first_id);
	    expr = xpath.compile("count(//healthMeasureHistory)");
	    int second_count = Integer.parseInt((String)expr.evaluate(doc, XPathConstants.STRING));
	    
	    // reset variable
	    start = "Request #9: POST /";
	    type = MediaType.APPLICATION_XML;
	    content = MediaType.APPLICATION_XML;
	    result = false;
	    request = "person/" + first_id + "/" + measure_type;
	    resp = this_resp;
	    xml = this_xml;
	    doc = this_doc;

	    if (count + 1 == second_count) result = true;
	    
		
	}
	
	
	
	private static void request1JSON() throws JsonProcessingException, IOException{
		// GET Request #1 --- GET  BASEURL/person
	    // Accept: application/json
	    //variable
	    start = "Request #1: GET /";
	    request = "person";
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    result = false;
	    
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    json = resp.readEntity(String.class);
	    JsonNode nodes = mapper.readTree(json);

	    System.out.println(nodes.size());
	    
	    if (nodes.size() > 2) result = true;
	    
	    
		
	}
	
	
	private static void request2JSON(String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // GET Request #2 --- GET  BASEURL/person/first_id
	    // Accept: application/json
	    //variable
	    start = "Request #2: GET /";
	    request = "person/"+id;
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    result = false;
	    
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    if (!xml.isEmpty())
	    	doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    if (resp.getStatus() == 200 || resp.getStatus() == 202) result = true;
	    
		
	}
	
	private static void request3JSON() throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // PUT Request #3 --- PUT  BASEURL/person/first_id
	    // Accept: application/json
	    //variable
	    start = "Request #3: PUT /";
	    request = "person/"+first_id;
	    type = MediaType.APPLICATION_JSON;
	    content = MediaType.APPLICATION_JSON;
	    result = false;
	    
	    String newName = "Jon";
	    String requestBody = "<person><name>"+ newName +"</name></person>";
	    resp = service.path(request).request().accept(type).put(Entity.entity(requestBody, content));
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));
    
	    XPathExpression expr = xpath.compile("//name");
	    String firstname = (String) expr.evaluate(doc, XPathConstants.STRING);
	    if (newName.equals(firstname)) result = true;
	    
	}
	
	
	
	private static String request4JSON() throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// POST Request #4 --- POST  BASEURL/person
	    // Accept: application/json
	    //variable
	    start = "Request #4: POST /";
	    request = "person";
	    type = MediaType.APPLICATION_JSON;
	    content = MediaType.APPLICATION_JSON;
	    result = false;
	    String newperson_id = "";
	    
	    String requestBody = "<person>"
	    		+ "<name>Chuck</name>"
	    		+ "<lastname>Norris</lastname>"
	    		+ "<birthdate>01/01/1945</birthdate>"
	    		+ "<healthProfile>"
	    		+ "<measureType><measureDef><idMeasureDef>1</idMeasureDef></measureDef><value>78.9</value></measureType>"
	    		+ "<measureType><measureDef><idMeasureDef>2</idMeasureDef></measureDef><value>172</value></measureType>"
	    		+ "</healthProfile></person>";


	    resp = service.path(request).request().accept(type).post(Entity.entity(requestBody, content));
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));
	    
	    XPathExpression expr = xpath.compile("//idPerson");
	    newperson_id = (String) expr.evaluate(doc, XPathConstants.STRING);
	    if ((resp.getStatus() == 200 || resp.getStatus() == 201 || resp.getStatus() == 202) && ! newperson_id.isEmpty()) result = true;
	    return newperson_id;
	    
	}
	
	
	private static void request5JSON(String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// DELETE Request #5 --- DELETE  BASEURL/person/id
	    // Accept: application/json
	    // variable
	    start = "Request #5: DELETE /";
	    request = "person/"+id;
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    doc = null;
	    

	    Response this_resp = service.path(request).request().accept(type).delete();
	    request2XML(id);
	    
	    // reset variable
	    start = "Request #5: DELETE /";
	    request = "person/"+id;
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    result = false;
	    
	    if (resp.getStatus()==404) result = true;
	    resp = this_resp;
	}
	
	private static String[] request6JSON() throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// GET Request #6 --- GET  BASEURL/measureTypes
	    // Accept: application/json
	    //variable
	    start = "Request #6: GET /";
	    request = "measureTypes";
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    result = false;
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    XPathExpression expr = xpath.compile("//*");
	    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	    
	    if (nodes.getLength() > 2) result = true;
	    String[] measureTypes= new String[nodes.getLength()-1];
 	    for (int i = 1; i< nodes.getLength(); i++){
	    	measureTypes[i-1]=nodes.item(i).getTextContent();
	    }
 	    return measureTypes;
	    
	}
	
	private static void request7JSON(String[] vector, String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
		// GET Request #7 --- GET  BASEURL/person/{id}/{measureType}
	    // Accept: application/json
	    //variable
	    start = "Request #7: GET /";
	    request = "person/"+ id + "/";
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    result = false;
	    Response this_res = null;
	    String this_req = null;
	    
	    for (int i = 0; i<vector.length; i++){
	    	String request1 = request + vector[i];
	    	
	    	resp = service.path(request1).request().accept(type).get();
	    	if(resp.getStatus() == 200){
	    		result = true;
	    		xml = resp.readEntity(String.class);
	    	    doc = builder.parse(new InputSource(new StringReader(xml)));	    	    
	    	    XPathExpression expr = xpath.compile("//healthMeasureHistory[1]/measureName");
	    	    Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    		measure_type = node.getTextContent();
	    		expr = xpath.compile("//healthMeasureHistory[1]/idMeasureHistory");
	    	    node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    		measure_id = node.getTextContent();
	    		id = first_id;
	    		this_res = resp;
	    		this_req = request1;
	    		
	    	}
	    	
	    }
	    
	    resp = this_res;
	    request = this_req;
	    
	}
	
	private static void request8JSON(String id) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // GET Request #8 --- GET  BASEURL/person/{id}/{measureType}/{mid}
	    // Accept: application/json
	    //variable
	    start = "Request #8: GET /";
	    request = "person/" + id + "/" + measure_type + "/" + measure_id;
	    type = MediaType.APPLICATION_JSON;
	    content = null;
	    result = false;
	    
	    
	    resp = service.path(request).request().accept(type).get();
	    
	    xml = resp.readEntity(String.class);
	    if (!xml.isEmpty()) 
	    	doc = builder.parse(new InputSource(new StringReader(xml)));

	    
	    if (resp.getStatus() == 200 || resp.getStatus() == 202) result = true;  
		
	}
	
	
	private static void request9JSON(String[] vector) throws SAXException, IOException, XPathExpressionException, TransformerException{	    
	    // GET Request #9 --- POST  BASEURL/person/{first_person_id}/{measureType}
	    // Accept: application/json
	    
	    
	    request7XML(vector, first_id);
	    XPathExpression expr = xpath.compile("count(//healthMeasureHistory)");
	    int count = Integer.parseInt((String)expr.evaluate(doc, XPathConstants.STRING));
	    
	    //variable
	    start = "Request #9: POST /";
	    type = MediaType.APPLICATION_JSON;
	    content = MediaType.APPLICATION_JSON;
	    result = false;
	    request = "person/" + first_id + "/" + measure_type;
	    String requestBody = "<healthMeasureHistory><value>72</value><created>09/12/2011</created></healthMeasureHistory>";


	    Response this_resp = service.path(request).request().accept(type).post(Entity.entity(requestBody, content));
	    String this_xml = this_resp.readEntity(String.class);
	    Document this_doc = builder.parse(new InputSource(new StringReader(this_xml)));
	    
	    request7XML(vector, first_id);
	    expr = xpath.compile("count(//healthMeasureHistory)");
	    int second_count = Integer.parseInt((String)expr.evaluate(doc, XPathConstants.STRING));
	    
	    // reset variable
	    start = "Request #9: POST /";
	    type = MediaType.APPLICATION_JSON;
	    content = MediaType.APPLICATION_JSON;
	    result = false;
	    request = "person/" + first_id + "/" + measure_type;
	    resp = this_resp;
	    xml = this_xml;
	    doc = this_doc;

	    if (count + 1 == second_count) result = true;
	    
		
	}
	
	
	
	
	

}