package Task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.app.hutbay.activity.ListingDetails;
import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

public class ContactAgentTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
   
    Context context;
    ListingDetails taskCaller;
    Button sendButton;
    
    
    public ContactAgentTask(Context context, ListingDetails taskCaller, Button sendButton){
    	 
    	this.context = context;
    	this.taskCaller = taskCaller;
    	this.sendButton = sendButton;
    }
    
    @Override
    protected void onPreExecute(){
      //show loading dialog
           	
    	if(NetworkManager.isConnectionAvailable(context)){
    		sendButton.setText("Sending...");
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
    	        nameValuePairs.add(new BasicNameValuePair("ListingRef", params[1]));
    	        nameValuePairs.add(new BasicNameValuePair("SenderEmail", params[2]));
    	        nameValuePairs.add(new BasicNameValuePair("SenderName", params[3]));
    	        nameValuePairs.add(new BasicNameValuePair("SenderPhoneNo", params[4]));
    	        nameValuePairs.add(new BasicNameValuePair("Note", params[5]));
    	        
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
        	ViewHelper.showGenericDialog(context, 
					"Error", 
					"Your request could not be sent at this moment, please try again.",
					"Retry"
					);  
   
        	taskCaller.disposeTask();
        }
        else{          	
        	 taskCaller.processResult(this.jsonResult);
        }   
        sendButton.setText("Send");
        sendButton.setEnabled(true);
    }

    @Override
    protected void onCancelled(){
    	cancel(true);        
    }
}

