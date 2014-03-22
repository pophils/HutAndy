package Task;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.app.entity.ListingDetailViews;
import com.app.hutbay.R;
import com.app.hutbay.R.drawable;
import com.app.hutbay.R.id;
import com.app.hutbay.activity.AllSearchResult;
import com.app.hutbay.activity.ListingDetails;
import com.app.hutbay.adapter.DetailsPhotoPagerAdapter;
import com.app.hutbay.fragment.DetailPhoto;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.ViewHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoadDetailsTask extends AsyncTask<String, Void, String> {     
    
    Context context;
    AllSearchResult taskCaller;      
    private JSONObject detailsJsonResult;
	private ListingDetailViews detailViews;
    boolean isCommercialListing = true;
	Intent detailsIntent;
	private DetailsPhotoPagerAdapter photoPagerAdapter;
	private ActionBar actionBar; 
	private Activity host; 
	
	
	
    public LoadDetailsTask(ListingDetailViews detailViews, Context context, Intent detailsIntent,
    		boolean isCommercialListing, ActionBar actionBar,  Activity host ){
    	this.detailViews = detailViews;
    	this.context = context;    
    	this.detailsIntent = detailsIntent;
    	this.isCommercialListing = isCommercialListing;
        this.actionBar = actionBar;
        this.host = host;
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
			detailsJsonResult = new JSONObject(jsonResult);
			
			if (detailsJsonResult != null) {			 
				
				 String title = detailsJsonResult.getString(Konstant.TAG_TITLE);
				 String price = detailsJsonResult.getString(Konstant.TAG_PRICE);
				 ViewHelper.ChangeActionBarTitle(context, actionBar, title);
			
				 actionBar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.custom_default_background));
			 
				 detailViews.price.setText(price);
		         detailViews.title.setText(title);
		         detailViews.priceTitle.setText(price);
		         detailViews.address.setText(detailsJsonResult.getString(Konstant.TAG_ADDRESS));
		         detailViews.fullAddress.setText(detailsJsonResult.getString(Konstant.TAG_FullAddress));		
		         detailViews.propertyType.setText(detailsJsonResult.getString(Konstant.TAG_PROPERTY_TYPE));
		         detailViews.numOfViews.setText(String.valueOf(detailsJsonResult.getInt(Konstant.TAG_TOTAL_NUM_OF_VIEWS)));
		         detailViews.description.setText(detailsJsonResult.getString(Konstant.TAG_DESCRIPTION)); 
		         detailViews.agentName.setText(detailsJsonResult.getString(Konstant.TAG_AGENT_NAME));			 
		         detailViews.companyName.setText(detailsJsonResult.getString(Konstant.TAG_COMPANY_NAME));
		         detailViews.listingWebUrl.setText(detailsJsonResult.getString(Konstant.TAG_LISTING_WEB_URL));
		         
		         if (detailsIntent.getBooleanExtra("isForsale", true)) {
		        	 detailViews.status.setText("For Sale");
				}
		         else {
		        	 detailViews.status.setText("To Rent");
				}
				
				
					String sqft = detailsJsonResult.getString(Konstant.TAG_SQFT);
					if(isCommercialListing){
					if(!sqft.isEmpty())
				         detailViews.sqft.setText(sqft);
					
					String parkingType = detailsJsonResult.getString(Konstant.TAG_PARKING_TYPE);
					if(!parkingType.isEmpty() && parkingType != "null")
				         detailViews.parkingType.setText(parkingType);
				}
				
				else{
			         detailViews.bed.setText(detailsJsonResult.getString(Konstant.TAG_BED));			 
			         detailViews.bath.setText(detailsJsonResult.getString(Konstant.TAG_BATH));
				}
		 
				
				String yearBuilt = detailsJsonResult.getString(Konstant.TAG_YEAR_BUILT);
				if(!yearBuilt.isEmpty() && yearBuilt != "null")
			         detailViews.yearBuilt.setText(yearBuilt);
				
				String dateAdded = detailsJsonResult.getString(Konstant.TAG_DATE_ADDED);
				if(!dateAdded.isEmpty() && dateAdded != null)
			         detailViews.dateAdded.setText(dateAdded);
				
			 
				String agentPhoneNo = detailsJsonResult.getString(Konstant.TAG_AGENT_PHONE);
				if(!agentPhoneNo.isEmpty() && agentPhoneNo != null)
			         detailViews.agentPhoneNo.setText(agentPhoneNo);
			  
			 

				String agentPhoto = detailsJsonResult.getString(Konstant.TAG_AGENT_PHOTO);
				if(!agentPhoto.isEmpty() && agentPhoto != null){
					 Picasso.with(context)
		       		  .load(Konstant.TAG_AGENT_PHOTO_URL + agentPhoto)       		  
		       		  .resize(70, 70)
		       		  .into(detailViews.agentPhoto); 
				}
				else {
					detailViews.agentPhoto.setVisibility(View.GONE);
					
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					
					params.setMargins(0, 0, 0, 0);
					detailViews.agentName.setLayoutParams(params);
					
					Resources r = context.getResources();
					
					int px = (int) TypedValue.applyDimension(
					        TypedValue.COMPLEX_UNIT_DIP,
					        30, 
					        r.getDisplayMetrics()
					);
					
					int px2 = (int) TypedValue.applyDimension(
					        TypedValue.COMPLEX_UNIT_DIP,
					        60, 
					        r.getDisplayMetrics()
					);
					
					
					params2.setMargins(0, px, 0, 0);
					params3.setMargins(0, px2, 0, 0);
					
					detailViews.agentPhoneNo.setLayoutParams(params2);					
					 
					detailViews.companyName.setLayoutParams(params3); 
				}
				 
				
				String companyLogoPth = detailsJsonResult.getString(Konstant.TAG_COMPANY_LOGO);
				if(!companyLogoPth.isEmpty() && companyLogoPth != null)
					  Picasso.with(context)
		       		  .load(Konstant.TAG_COMPANY_LOGO_URL + companyLogoPth)       		  
		       		  .resize(40, 40)
		       		  .into(detailViews.companyLogo); 
				
				
				JSONArray detailphotos = detailsJsonResult.getJSONArray(Konstant.TAG_DETAIL_PHOTOS);
				
				if (detailphotos.length() > 0) {			
					
					List<String> photoPaths =  new Vector<String>();

					  for (int photoPos = 0; photoPos < detailphotos.length(); photoPos++) {						
						  photoPaths.add(detailphotos.getString(photoPos));	
					}					  
					  photoPagerAdapter = new DetailsPhotoPagerAdapter(photoPaths, context);					  
					  detailViews.photosViewPager.setAdapter(photoPagerAdapter);  
					
				}	
				else{					
					
					 List<String> photoPaths =  new Vector<String>();

					  photoPaths.add("nophotos");					  
					  photoPagerAdapter = new DetailsPhotoPagerAdapter(photoPaths, context);					  
					  detailViews.photosViewPager.setAdapter(photoPagerAdapter);  
					  
				}
				
			((ListingDetails)host).setDetailBtnListener();
			
			}			
			else {
				Toast.makeText(context,jsonResult, Toast.LENGTH_LONG) // display alert dialog later
				.show();
			}
			
		} catch (JSONException e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG) // display alert dialog later
			.show(); 
		}   
    }
 
}

