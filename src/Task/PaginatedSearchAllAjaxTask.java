package Task;

import com.app.hutbay.activity.AllSearchResult;
import com.app.hutbay.utility.NetworkManager;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PaginatedSearchAllAjaxTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    Context context;
    AllSearchResult taskCaller;
    RelativeLayout paginationLoadingLayt;
    
    public PaginatedSearchAllAjaxTask(Dialog dialog, Context context, AllSearchResult allSearchResult,  RelativeLayout paginationLoadingLayt){    	 
    	this.context = context;
    	taskCaller = allSearchResult;
    	this.paginationLoadingLayt =  paginationLoadingLayt;
    }
    
    @Override
    protected void onPreExecute(){     
           	
    	if(NetworkManager.isConnectionAvailable(context)){
    		paginationLoadingLayt.setVisibility(paginationLoadingLayt.VISIBLE);    
    	}    	
    	else{
    		paginationLoadingLayt.setVisibility(paginationLoadingLayt.GONE);
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
        	paginationLoadingLayt.setVisibility(paginationLoadingLayt.GONE);
        	Toast.makeText(context, "No more properties could be loaded at this time, please try again.", Toast.LENGTH_LONG) // display alert dialog later
    		.show();   
        	taskCaller.disposeTask();
        }
        else{        
        	paginationLoadingLayt.setVisibility(paginationLoadingLayt.GONE);
        	taskCaller.processPaginatedResult(this.jsonResult);
        }    
    }

    @Override
    protected void onCancelled(){
    	cancel(true);
    	paginationLoadingLayt.setVisibility(paginationLoadingLayt.GONE);     
    }
}

