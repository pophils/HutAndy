package Task;

import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SearchAllAjaxTask extends AsyncTask<String, Void, String> {
     
    private String jsonResult;
    int orientation;
    Dialog loadingDialog;
    Context context;
    SearchAll taskCaller;
    
    
    public SearchAllAjaxTask(Dialog dialog, Context context, SearchAll searchAll){
    	this.loadingDialog = dialog;
    	this.context = context;
    	taskCaller = searchAll;
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
    		taskCaller.disposeTask();
    	}
    	
       //orientation = getActivity().getRequestedOrientation();
      // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
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

