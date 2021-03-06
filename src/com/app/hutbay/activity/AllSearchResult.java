package com.app.hutbay.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.app.entity.Listing;
import com.app.hutbay.Landing;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.R.layout;
import com.app.hutbay.adapter.DrawerAdapter;
import com.app.hutbay.adapter.ListingAdapter;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.ViewHelper;

import Task.LoadAllSearchResultTask;
import Task.PaginatedSearchAllAjaxTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
 
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AllSearchResult extends SherlockFragmentActivity {	
	
	public static final String loadingGifDialogFromLanding = "loadingGif";
	public static boolean isForSaleResult = true;
	public static boolean isCommercialDetailListingLoad;
	private ListView resultListView;	
	private ListingAdapter listingAdapter;	
	private ArrayList<Listing> listings = new ArrayList<Listing>();	
	private Listing listing;
	public static TextView searchHeader;
	public static int currentResultIndex;
	public static int currentNumOfListingFetched;
	private PaginatedSearchAllAjaxTask ajaxTask;
	private String paginationUrl;
	private JSONObject listingJsonResult;
	private JSONArray listingsJson;
    private DrawerLayout drawer;    
	private ListView drawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private  RelativeLayout paginationLoadingLayt;
    public static boolean isloadingMoreResult;
    public static int totalPgNo;
	public static CharSequence listingRef;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		new ViewHelper().initConnectionCheck(this);
		
		super.onCreate(savedInstanceState);			 
		 ViewHelper.ChangeActionBarBackgroundColor(getBaseContext(), getSupportActionBar());		 
		 setContentView(R.layout.act_all_search_result);	 

		initResultListView();   
		paginationUrl = getResources().getString(R.string.getAllListingUrl);
		
		Intent searchIntent = getIntent();		
		LoadAllSearchResultTask loadAllSearchResultTask = new LoadAllSearchResultTask(this, searchIntent, this);
		loadAllSearchResultTask.execute(searchIntent.getStringExtra(Konstant.TAG_JSON_RESULT_XTRA));	 
		
		
		initDrawerLayout();
	}

	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        new MenuInflater(this).inflate(R.menu.all_search_result, menu);
	       
	        return(super.onCreateOptionsMenu(menu));
	    }
	
	
	private void initResultListView(){		
		resultListView = (ListView)findViewById(id.search_result_listview);
		listingAdapter = new ListingAdapter(getBaseContext(), layout.search_result_list_row, listings, resultListView, this);
		resultListView.setAdapter(listingAdapter);
		listingAdapter.setCustomScrollListener();
		listingAdapter.setCustomClickListener(true);
		paginationLoadingLayt = (RelativeLayout)findViewById(id.pagination_loading_rel_lyt);
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		 if(paginationLoadingLayt == null)
				paginationLoadingLayt = (RelativeLayout)findViewById(id.pagination_loading_rel_lyt);
		     
		     LinearLayout.LayoutParams  params = (LinearLayout.LayoutParams) paginationLoadingLayt.getLayoutParams();
		     if(newConfig.orientation ==  Configuration.ORIENTATION_LANDSCAPE){
		     	
		     	params.weight = 12;
		     	paginationLoadingLayt.setLayoutParams(params);
		     }
		     else{
		     	  params.weight = 6;
		     	 paginationLoadingLayt.setLayoutParams(params);
		     }
	}

	
    public boolean onOptionsItemSelected(MenuItem item)
    {
	        if (item.getItemId() == android.R.id.home) {

	            if (drawer.isDrawerOpen(drawerListView)) {
	                drawer.closeDrawer(drawerListView);
	            } else {
	                drawer.openDrawer(drawerListView);
	            }
	        }	          
	        
	        if (item.getItemId() == R.id.filter_search_menu) {
 
	        	  Intent i = new Intent(AllSearchResult.this, Landing.class);
	        	  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        	  startActivity(i);	              
	               
	        }

	        return super.onOptionsItemSelected(item);
	}


	    @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {

	        if(keyCode == KeyEvent.KEYCODE_MENU){

	            if (drawer.isDrawerOpen(drawerListView)) {
	                drawer.closeDrawer(drawerListView);
	            } else {
	                drawer.openDrawer(drawerListView);
	            }
	            return true;
	        }	        
	  
	        return super.onKeyDown(keyCode, event);
	    }
	
@Override
   protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        drawerToggle.syncState();
	    }


   public void loadListRow(JSONObject listingObj){
	
	listing = new Listing();
	try {
		listing.cityState = listingObj.getString(Konstant.TAG_CITY_STATE);
		listing.title = listingObj.getString(Konstant.TAG_TITLE);
		listing.price = listingObj.getString(Konstant.TAG_PRICE);
		listing.address = listingObj.getString(Konstant.TAG_ADDRESS);	
		listing.primaryPhoto = listingObj.getString(Konstant.TAG_PRIMARY_PHOTO);
		listing.listingRef = listingObj.getString(Konstant.TAG_LISTING_REF);
		listing.isCommercial = listingObj.getBoolean(Konstant.TAG_IS_COMMERCIAL);
	} catch (JSONException e) {
		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG) // display alert dialog later
		.show(); 
	}
	
	 
	listings.add(listing);
	listingAdapter.notifyDataSetChanged();
}

   public void updateSearchHeader(boolean isForSaleSearch){
	
		if(isForSaleSearch)
		searchHeader.setText( currentNumOfListingFetched + " of All Properties For Sale");
		else{
			 searchHeader.setText(currentNumOfListingFetched + " of All Properties To Rent");			
		}	   	 
}

   private void initDrawerLayout(){

    final String[] drawerItems = getResources().getStringArray(R.array.drawer_list_item_values_sr);
    drawer = (DrawerLayout) findViewById(R.id.landing_drawer_layout);
    drawerListView = (ListView) findViewById(R.id.landing_drawer);

    DrawerAdapter adapter = new DrawerAdapter(true, getSupportFragmentManager(), AllSearchResult.this, R.layout.drawer_list_row, drawerItems, drawerListView, drawer);
    drawerListView.setAdapter(adapter);

    adapter.setCustomItemClickListener();

    drawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_drawer,R.string.open_drawer, R.string.close_drawer) {

        public void onDrawerClosed(View view) {
            getActionBar().setTitle(getSupportActionBar().getTitle());
        }

        public void onDrawerOpened(View drawerView) {
           // getActionBar().setTitle("Open Drawer");
        }
    };

    drawer.setDrawerListener(drawerToggle);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
}

   public void processPaginatedResult(String jsonResult){
  
	
       ajaxTask.cancel(true);		
   
		try {
			listingJsonResult = new JSONObject(jsonResult);
			listingsJson = listingJsonResult.getJSONArray("ListingsJson");
			
			for (int pos = 0; pos < listingsJson.length(); pos++) {
				JSONObject listingObj = listingsJson.getJSONObject(pos);					
				loadListRow(listingObj);				  			
			}
			
		} catch (JSONException e) {		
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG) 
    		.show();  
		}				
		
		currentNumOfListingFetched = currentNumOfListingFetched + listingsJson.length();
		currentResultIndex = currentResultIndex + 1;			
		updateSearchHeader(isForSaleResult);	
		listingJsonResult = null;
		listingsJson = null; 
		isloadingMoreResult = false;	
}

   public void paginateResult(){	
	
	ajaxTask = new PaginatedSearchAllAjaxTask(null, getBaseContext(), this, paginationLoadingLayt);	
 
	if(isForSaleResult){
		ajaxTask.execute(paginationUrl + "?type=1&pageNo="+(currentResultIndex+1));	  
	}
	else{
		ajaxTask.execute(paginationUrl + "?type=2&pageNo="+(currentResultIndex+1));
	} 
}

   public void disposeTask(){
	ajaxTask = null;
}

   public void redirectToDetails(String jsonResult){	
	    Intent loadDetailsActivity = new Intent(this, ListingDetails.class);

	    loadDetailsActivity.putExtra(Konstant.TAG_IS_COMMERCIAL, isCommercialDetailListingLoad);	    
	    loadDetailsActivity.putExtra(Konstant.TAG_JSON_RESULT_XTRA, jsonResult);
	    loadDetailsActivity.putExtra(Konstant.TAG_LISTING_REF_XTRA, listingRef);
		startActivity(loadDetailsActivity);
}

 

}
