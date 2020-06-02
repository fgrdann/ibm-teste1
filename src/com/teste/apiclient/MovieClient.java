package com.teste.apiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieClient {

	/** 
	 * Given a string, query an API for movies containing this string in movie title
	 * 
	 * @param substr 
	 * @return String Array containing movie titles matching the given string
	 * @author Daniel Rodrigues
	 * */
	public String[] getMovieTitles(String substr) {
		
			JSONObject json = getMoviesJSON(substr,1);
			
			int total = json.getInt("total");
			
			if (total == 0) {
				return null;
			}
			
			int totalPages = json.getInt("total_pages");
			int currentPage = json.getInt("page");
			int movieCount = 0;
			String[] titles = new String[total];
			
			
			while (currentPage <= totalPages) {
				if (currentPage > 1) {
					json = getMoviesJSON(substr,currentPage);
				}
				JSONArray data = new JSONArray(json.get("data").toString());
				
				for (int i = 0; i < data.length(); i++) {
					JSONObject movie = new JSONObject(data.get(i).toString());
					titles[movieCount+i] = movie.getString("Title");
				}
				movieCount = data.length();
				currentPage++;
			}
			
			titles = sortTitles(titles);
			
			return titles;
			
	}
	
	/** 
	 * Sort titles
	 * 
	 * @param titles the String Array 
	 * @return the String Array containing sorted titles - ascending
	 * @author Daniel Rodrigues
	 * */	
	private String[] sortTitles(String[] titles) {
		int size = titles.length;
		
		for(int i = 0; i<size-1; i++) {
			for (int j = i+1; j<titles.length; j++) {
				if(titles[i].compareTo(titles[j])>0) {
					String temp = titles[i];
					titles[i] = titles[j];
					titles[j] = temp;
				}
			}
		}
		return titles;
	}
	
	/** 
	 * The actually query for movie titles, considering page number of results
	 * 
	 * @param substr the string to search
	 * @param page the page to get
	 * @return JSON containing the results from this page
	 * @author Daniel Rodrigues
	 * */
	private JSONObject getMoviesJSON(String substr, int page) {
		URL url;
		JSONObject res = new JSONObject();
		try {
			Map<String, String> parameters = new HashMap<>();
			parameters.put("Title", substr);
			parameters.put("page", String.valueOf(page));
			
			System.setProperty("javax.net.ssl.trustStore", "keystore.jks");

			url = new URL("https://jsonmock.hackerrank.com/api/movies/search/?"+getQueryParams(parameters));
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
						
			BufferedReader in = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));	
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			res = new JSONObject(content.toString());
			con.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/** 
	 * Transform a map of parameters into encoded URL, to be used as query parameters in HTTP request
	 * 
	 * @param params the map containing all params
	 * @return the encoded string
	 * @author Daniel Rodrigues
	 * */	
    private String getQueryParams(Map<String, String> params) 
      throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
 
        for (Map.Entry<String, String> entry : params.entrySet()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          result.append("&");
        }
 
        String resultString = result.toString();
        return resultString.length() > 0
          ? resultString.substring(0, resultString.length() - 1)
          : resultString;
    }
}