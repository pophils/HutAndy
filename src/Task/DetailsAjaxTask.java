package Task;

import com.app.hutbay.activity.AllSearchResult;
import com.app.hutbay.activity.LocationSearchResult;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DetailsAjaxTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
    Dialog loadingDialog;
    Context context;
    AllSearchResult taskCaller;
    LocationSearchResult taskCallerLoc;
	boolean isAllListingSearch;      
    
    public DetailsAjaxTask(Dialog dialog, Context context, Activity activity, boolean isAllListingSearch){
    	this.loadingDialog = dialog;
    	this.context = context;     
    	this.isAllListingSearch = isAllListingSearch;
    	
    	if(isAllListingSearch)
    		taskCaller = (AllSearchResult)activity;
    	else
    		taskCallerLoc = (LocationSearchResult)activity;
    }
    
    @Override
    protected void onPreExecute(){
      //show loading dialog
           	
    	if(NetworkManager.isConnectionAvailable(context)){
    		loadingDialog.show();
    	}
    	
    	else{
    		cancel(true);
    		Toast.makeText(context, "Please check your connection and try again.", Toast.LENGTH_LONG)
    		.show();   
    		//disposeTask();  no need for dispose(throws exception if used after cancel) after cancel have been called
    	}
    }

    @Override
    protected String doInBackground(String... urls){
       	
    	try{    		
    		return NetworkManager.getJsonFromUrl(urls[0]);
    	}
    	catch(Exception ex){
    		disposeTask();
    	}
    	
        return null;
    } 

    @Override
    protected void onPostExecute(String jsonResult) {
        this.jsonResult = jsonResult;
        
        if( jsonResult == null || jsonResult.trim().isEmpty()){
        	loadingDialog.dismiss();
        	
        	ViewHelper.showGenericDialog(context, 
					"Property Details not found.", 
					"No details was found for this property at the moment, please try again.",
					"Retry"
					);        	
        	  
        	disposeTask();
        }
        else{           	
        	cancel(true); 
        	if (isAllListingSearch) 				
			taskCaller.redirectToDetails(this.jsonResult);
        	else 
        	taskCallerLoc.redirectToDetails(this.jsonResult);
        	loadingDialog.dismiss();
        }    
    }

    @Override
    protected void onCancelled(){
    	cancel(true);
    	loadingDialog.dismiss();       
    }
    
    public void disposeTask(){
    	disposeTask();
    }
}

