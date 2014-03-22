package Task;

import com.app.hutbay.activity.Alert;
import com.app.hutbay.fragment.LocationSearch;
import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class LoadCityAjaxTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
    Dialog loadingDialog;
    Context context;
    Alert taskCaller;
    
    
    public LoadCityAjaxTask(Context context, Alert taskCaller){
    	 
    	this.context = context;
    	this.taskCaller = taskCaller;
    }
    
    @Override
    protected void onPreExecute(){
      
    	if(NetworkManager.isConnectionAvailable(context)){
    		 
    	}    	
    	else{
    		cancel(true);
    		Toast.makeText(context, "Could not load load cities this time, Please check your connection and try again.", Toast.LENGTH_LONG)
    		.show();   
    		taskCaller.disposeTask();
    	}    	 
    }

    @Override
    protected String doInBackground(String... urls){
       	
    	try{    		
    		return NetworkManager.getJsonFromUrl(urls[0]);
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
         	taskCaller.disposeTask();
        }
        else{        	
        	taskCaller.processCityResult(this.jsonResult);
        }    
    }

    @Override
    protected void onCancelled(){
    	cancel(true);
    	     
    }
}

