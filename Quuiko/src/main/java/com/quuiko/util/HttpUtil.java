package com.quuiko.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.quuiko.exception.QRocksException;

public class HttpUtil {
	private static final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
 
		HttpUtil http = new HttpUtil();
		Map<String,String> params=new HashMap<String, String>();
		params.put("Authorization", "key=312556914872");
		Map<String,String> headers=new HashMap<String, String>();
		headers.put("Authorization", "key=AIzaSyBULi6MYYkHIMNw6tWVSQKAPQUxdHBVfqU");
		headers.put("Content-Type","application/json");
		HttpUtil.sendPost("https://android.googleapis.com/gcm/send",headers, null);
 
//		System.out.println("Testing 1 - Send Http GET request");
//		http.sendGet();
// 
//		System.out.println("\nTesting 2 - Send Http POST request");
//		http.sendPost();
 
	}
 
	// HTTP GET request
	private void sendGet() throws Exception {
 
		String url = "http://www.google.com/search?q=mkyong";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
 
	// HTTP POST request
	private void sendPost() throws Exception {
 
		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
	
	public static void sendPost(String url,Map<String, String> headers,Map<String, String> params) throws QRocksException{
		try{
			StringBuilder urlParameters=new StringBuilder("");
			if(!Utileria.isEmptyMap(params)){
				for(String k:params.keySet()){
					urlParameters.append(k+"="+params.get(k));
				}
			}
			url+="?"+urlParameters.toString();
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	 
			//add reuqest header
			con.setRequestMethod("POST");
			if(!Utileria.isEmptyMap(headers)){
				for(String k:headers.keySet()){
					con.setRequestProperty(k, headers.get(k));
				}
			}
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters.toString());
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
		} catch (IOException e) {
			Utileria.onError("Ocurrio un error al enviar la informacion a la url de destino.");
		}
	}
	
	public static void sendGet(String url,Map<String, String> headers, Map<String,String> params) throws QRocksException {
		try{
			StringBuilder urlParameters=new StringBuilder("");
			if(!Utileria.isEmptyMap(params)){
				for(String k:params.keySet()){
					urlParameters.append(k+"="+params.get(k));
				}
			} 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
			if(!Utileria.isEmptyMap(headers)){
				for(String k:headers.keySet()){
					con.setRequestProperty(k, headers.get(k));
				}
			}
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
		} catch (IOException e) {
			Utileria.onError("Ocurrio un error al enviar la informacion a la url de destino.");
		}
	}
}
