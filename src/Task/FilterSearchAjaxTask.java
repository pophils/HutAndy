package Task;

import com.app.hutbay.fragment.LocationSearch;
import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class FilterSearchAjaxTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
    Dialog loadingDialog;
    Context context;
    LocationSearch taskCaller;
    
    
    public FilterSearchAjaxTask(Dialog dialog, Context context, LocationSearch taskCaller){
    	this.loadingDialog = dialog;
    	this.context = context;
    	this.taskCaller = taskCaller;
    }
    
    @Override
    protected void onPreExecute(){
      
    	if(NetworkManager.isConnectionAvailable(context)){
    		loadingDialog.show();
    	}    	
    	else{
    		cancel(true);
    		Toast.makeText(context, "Please check your connection and try again.", Toast.LENGTH_LONG)
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
        	loadingDialog.dismiss();
        	
        	ViewHelper.showGenericDialog(context, 
					"Property not found.", 
					"No property was found for your search, please try again.",
					"Retry"
					);        	
        	        	taskCaller.disposeTask();
        }
        else{        	
        	taskCaller.processResult(this.jsonResult);
        }    
    }

    @Override
    protected void onCancelled(){
    	cancel(true);
    	loadingDialog.dismiss();       
    }
}

