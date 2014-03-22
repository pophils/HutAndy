package com.app.hutbay.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {	

	 public static String getJsonFromUrl(String url) throws Exception{
		   StringBuilder stringBuilder = new StringBuilder();
	        
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        
	        HttpResponse response = httpClient.execute(httpGet);	        
	        StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	        
	        if (statusCode == 200) {
	            HttpEntity httpEntity = response.getEntity();
	            InputStream responseStream = httpEntity.getContent();
	            BufferedReader responseReader = new BufferedReader(
	                                    new InputStreamReader(responseStream));
	            String responseLine;
	           
	            while ((responseLine = responseReader.readLine()) != null && !Thread.interrupted()) {
	                stringBuilder.append(responseLine);
	            }
	            
	            responseStream.close();
	        } 
	        else {

	        }

	        return stringBuilder.toString();
	    }

	 public static boolean isConnectionAvailable(Context context) {
	    	
	        ConnectivityManager connectivityManager
	                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        
	        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	        
	        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	    }

     public static String makePostRequest(Context context, String url,  List<NameValuePair> nameValuePairs ){
    	    	 
    	 HttpClient httpClient = new DefaultHttpClient();
         HttpPost httpPost = new HttpPost(url);
         StringBuilder stringBuilder = null;
         
         try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    try {
				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				 InputStream inputStream = httpResponse.getEntity().getContent();

                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                 BufferedReader bufferedReader;
                 bufferedReader = new BufferedReader(inputStreamReader);

                 stringBuilder = new StringBuilder();

                 String bufferedStrChunk = null;

                 while((bufferedStrChunk = bufferedReader.readLine()) != null){
                     stringBuilder.append(bufferedStrChunk);
                 }
                
			}

		    catch (ClientProtocolException e) {
			
		    	return null;
			} 
		    
		    catch (IOException e) {
				
		    	return null;
			}
			
		  } 
         
         catch (UnsupportedEncodingException e) {
			
        	 return null;
		}   
      
		return stringBuilder.toString();    	 
     }
}
