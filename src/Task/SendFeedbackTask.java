package Task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.app.hutbay.Landing;
import com.app.hutbay.activity.FeedBack;
import com.app.hutbay.activity.ListingDetails;
import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

public class SendFeedbackTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
   
    Context context;
    FeedBack taskCaller;
    Button sendButton;
    
    
    public SendFeedbackTask(Context context, FeedBack taskCaller, Button sendButton){
    	 
    	this.context = context;
    	this.taskCaller = taskCaller;
    	this.sendButton = sendButton;
    }
    
    @Override
    protected void onPreExecute(){     
           	
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
    	        nameValuePairs.add(new BasicNameValuePair("Email", params[1]));
    	        nameValuePairs.add(new BasicNameValuePair("Phone", params[2]));
    	        nameValuePairs.add(new BasicNameValuePair("Message", params[3]));
    	        nameValuePairs.add(new BasicNameValuePair("Name", params[4]));
    	             
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
        	
        	taskCaller.showErrorMessage(this.jsonResult);        	
   
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

