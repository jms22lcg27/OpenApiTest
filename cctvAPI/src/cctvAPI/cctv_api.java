package cctvAPI;

import java.util.ArrayList;


import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;

public class cctv_api {
 public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
  StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/cctvInfo"); /*URL*/
  urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode("KEY", "UTF-8")); /*공개키*/
  urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8")); /*도로유형*/
  urlBuilder.append("&" + URLEncoder.encode("cctvType","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*CCTV유형*/
  urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode("126.800000", "UTF-8")); /*최소경도영역*/
  urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode("127.890000", "UTF-8")); /*최대경도영역*/
  urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode("34.900000", "UTF-8")); /*최소위도영역*/
  urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode("35.100000", "UTF-8")); /*최대위도영역*/
  urlBuilder.append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*출력타입*/
  URL url = new URL(urlBuilder.toString());
  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
  conn.setRequestMethod("GET");
  conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");
  System.out.println("Response code: " + conn.getResponseCode());
  BufferedReader rd;
  if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
   rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
  } else {
   rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
  }
  StringBuilder sb = new StringBuilder();
  String line;
  while ((line = rd.readLine()) != null) {
   sb.append(line);
  }
  rd.close();
  conn.disconnect();
  /*System.out.println(sb.toString());*/
  
  
  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  //Document로 파싱 하여 사용 할 것이기에 DocumentBuilderFactory 선언
  DocumentBuilder builder = factory.newDocumentBuilder();
  //DocumentBuilderFactory로 DocumentBuilder
  Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
  //sb.toString을 Document 형식으로 저장
  
  
  
  ArrayList<String> list = new ArrayList<String>();
  list.add("cctvname");
  list.add("coordx");
  list.add("coordy");
  list.add("cctvurl");
  
  
  
  ArrayList<ArrayList<String>> cctvlist = new ArrayList<ArrayList<String>>();
  
  NodeList taglist1 = document.getElementsByTagName("cctvname");
  
  for(int j=0; j<taglist1.getLength();j++) {
	  ArrayList<String> a = new ArrayList<String>();
	  for (int i=0; i<list.size(); i++) {
		  
		  NodeList taglist = document.getElementsByTagName(list.get(i));
		  //document 안에서 찾고자 하는 태그값을 가져 와서 NodeList로 저장
		  Node tagtext = taglist.item(j).getChildNodes().item(0);
		  //NodeList는 List 형태 이기에 Node로 변환 하여 저장
		  String Tag = tagtext.getNodeValue();
		  
		  a.add(Tag);
	  }
	  cctvlist.add(a);
  }
  
  
  for(int i=0; i<cctvlist.size();i++) {
	  System.out.println(cctvlist.get(i));
  }
  
 }
}