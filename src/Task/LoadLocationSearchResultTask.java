package Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.app.entity.ListingDetailViews;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.activity.AllSearchResult;
import com.app.hutbay.activity.LocationSearchResult;
import com.app.hutbay.fragment.LocationSearch;
import com.app.hutbay.fragment.SearchAll;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.NetworkManager;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class LoadLocationSearchResultTask extends AsyncTask<String, Void, String> {     
    
    Context context;
    LocationSearchResult taskCaller;   
	Intent searchIntent;
	private JSONObject listingJsonResult;
	private JSONArray listingsJson;
	
	public LoadLocationSearchResultTask(Activity context, Intent searchIntent, LocationSearchResult taskCaller){
    	this.context = context;    
    	this.searchIntent = searchIntent;   
    	this.taskCaller = taskCaller;
    }
    
    @Override
    protected void onPreExecute(){
 
    }

    @Override
    protected String doInBackground(String... urls){
          return urls[0];
    } 

    @Override
    protected void onPostExecute(String jsonResult) {
    	
		try {
			listingJsonResult = new JSONObject(jsonResult);
			
			listingsJson = listingJsonResult.getJSONArray("ListingsJson");	
		    	 			
			for (int pos = 0; pos < listingsJson.length(); pos++) {
				JSONObject listingObj = listingsJson.getJSONObject(pos);					
				taskCaller.loadListRow(listingObj);				  			
			}			
			 
			if(LocationSearch.loadingGifDialog != null)
				LocationSearch.loadingGifDialog.dismiss();				
		 
			
			LocationSearchResult.currentNumOfListingFetched = listingsJson.length();
			LocationSearchResult.currentResultIndex = 1;	
			LocationSearchResult.searchHeader = (TextView) ((LocationSearchResult) context).findViewById(id.search_result_header_lbl);		
			taskCaller.updateSearchHeader(searchIntent.getBooleanExtra(Konstant.TAG_IS_FORSALE_SEARCH_XTRA, true), listingJsonResult.getString(Konstant.TAG_LOCATION));
			
			LocationSearchResult.stateId = listingJsonResult.getInt(Konstant.TAG_STATE_ID);
			LocationSearchResult.cityId = listingJsonResult.getInt(Konstant.TAG_CITY_ID);
			LocationSearchResult.totalPgNo = listingJsonResult.getInt(Konstant.TAG_TOTAL_PAGE_NO);
			
			listingJsonResult = null;
			listingsJson = null;
			 
		} 
		catch (JSONException e) {			 
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG) // display alert dialog later
    		.show();  
			//go to a customized page for error
		} 
    }
 
}

