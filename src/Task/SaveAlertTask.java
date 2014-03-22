package Task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.app.hutbay.activity.Alert;
import com.app.hutbay.activity.ListingDetails;
import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

public class SaveAlertTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
   
    Context context;
    Alert taskCaller;
    Button sendButton;
    
    
    public SaveAlertTask(Context context, Alert taskCaller, Button sendButton){
    	 
    	this.context = context;
    	this.taskCaller = taskCaller;
    	this.sendButton = sendButton;
    }
    
    @Override
    protected void onPreExecute(){
      //show loading dialog
           	
    	if(NetworkManager.isConnectionAvailable(context)){
    		sendButton.setText("Saving...");
    		sendButton.setEnabled(false);
    	}
    	
    	else{
    		cancel(true);
    		Toast.makeText(context, "Please check your connection and try again.", Toast.LENGTH_LONG)
    		.show();   
    		taskCaller.disposeTask();
    	}   	
     
    }

    @Override
    protected String doInBackground(String... params){		 
				
				
				try{     		
    		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

    	        nameValuePairs.add(new BasicNameValuePair("AdvertTypeId", params[1]));
    	        nameValuePairs.add(new BasicNameValuePair("SubscriberEmail", params[2]));    	        
    	        nameValuePairs.add(new BasicNameValuePair("ListingTypeId", params[3]));    	        
    	        nameValuePairs.add(new BasicNameValuePair("StateId", params[4]));
    	        nameValuePairs.add(new BasicNameValuePair("CityId", params[5]));
    	        nameValuePairs.add(new BasicNameValuePair("Beds", params[6]));
    	        nameValuePairs.add(new BasicNameValuePair("MaxPrice", params[7]));
    	        nameValuePairs.add(new BasicNameValuePair("NotificationSetting", params[8]));
    	        
    	        return NetworkManager.makePostRequest(context, params[0], nameValuePairs);    	 
    	}
    	catch(Exception ex){
    		taskCaller.disposeTask();
    	}
    	
        return null;
    } 

    @Override
    protected void onPostExecute(String jsonResult) {
        this.jsonResult = jsonResult;
        
        if( jsonResult == null || jsonResult.trim().isEmpty()){         
        	
        	taskCaller.showErrorDialog("Your property alert could not be saved at this moment, please try again.");
        
        	taskCaller.disposeTask();
        }
        else{          	
        	 taskCaller.processResult(this.jsonResult);
        }   
        sendButton.setText("Save");
        sendButton.setEnabled(true);
    }

    @Override
    protected void onCancelled(){
    	cancel(true);        
    }
}

