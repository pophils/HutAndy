package Task;

import com.app.hutbay.activity.LocationSearchResult;
import com.app.hutbay.utility.NetworkManager;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PaginatedLocSearchAjaxTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    Context context;
    LocationSearchResult taskCaller;
    RelativeLayout paginationLoadingLayt;
    
    public PaginatedLocSearchAjaxTask(Dialog dialog, Context context, LocationSearchResult taskCaller,  RelativeLayout paginationLoadingLayt){    	 
    	this.context = context;
    	this.taskCaller = taskCaller;
    	this.paginationLoadingLayt =  paginationLoadingLayt;
    }
    
    @Override
    protected void onPreExecute(){     
           	
    	if(NetworkManager.isConnectionAvailable(context)){
    		paginationLoadingLayt.setVisibility(View.VISIBLE);    
    	}    	
    	else{
    		paginationLoadingLayt.setVisibility(View.GONE);
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
        	paginationLoadingLayt.setVisibility(View.GONE);
        	Toast.makeText(context, "No more properties could be loaded at this time, please try again.", Toast.LENGTH_LONG) 
    		.show();   
        	taskCaller.disposeTask();
        }
        else{        
        	paginationLoadingLayt.setVisibility(View.GONE);
        	taskCaller.processPaginatedResult(this.jsonResult);
        }    
    }

    @Override
    protected void onCancelled(){
    	cancel(true);
    	paginationLoadingLayt.setVisibility(View.GONE);     
    }
}

