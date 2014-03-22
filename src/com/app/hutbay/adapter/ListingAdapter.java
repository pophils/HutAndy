package com.app.hutbay.adapter;

import Task.DetailsAjaxTask;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.app.entity.Listing;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.activity.AllSearchResult;
import com.app.hutbay.activity.LocationSearchResult;
import com.app.hutbay.dialog.LoadingGif;
import com.app.hutbay.utility.Konstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

 
 
public class ListingAdapter extends ArrayAdapter<Listing> implements OnScrollListener, OnItemClickListener {

    private Context context;
    private  ArrayList<Listing> listings;
    private ListView listView;
    private SherlockFragmentActivity host;
    private DetailsAjaxTask detailsAjaxTask;
    private LoadingGif progressGif;
	private boolean isAllLocSearch; 
	
   public ListingAdapter(Context context, int rowViewResourceId, ArrayList<Listing> listings, ListView listView, SherlockFragmentActivity host ){
        super(context, rowViewResourceId, listings);
        this.context = context;
        this.listings = listings;
        this.listView = listView;
        this.host = host;          
        progressGif = new LoadingGif(host);
        setDialogOnkeyListener();        
     }

    @Override
    public View getView(int itemPosition, View convertView, ViewGroup root) {

        View row = convertView;
        ListingTemplate template;

        if(row == null){ // render new row
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             
             row = inflater.inflate(R.layout.search_result_list_row, root, false);
             template = new ListingTemplate(row);
             row.setTag(template);
        }
        else{
            template = (ListingTemplate)row.getTag();
        }

        template.LoadValuesFrom(listings.get(itemPosition));

        return row;
    } 
 
    
private void setDialogOnkeyListener(){
		
	      progressGif.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_BACK){
                    
                	try{
                		detailsAjaxTask.cancel(true);
                	}
                	catch(Exception ex){
                		return false;
                	}	                	
                }
                return false;
            }
	});
	
	}

// this serve as a template for each listView row
    class ListingTemplate{

        private View row;
        private TextView cityState;
        private TextView title;
        private TextView price;
        private TextView address;
        private TextView listingRef;
        private ToggleButton isCommercial;
        private ImageView listingPhoto;


        ListingTemplate(View row){
             this.row =row;
             this.cityState = (TextView)this.row.findViewById(R.id.cityState);
             this.title = (TextView)this.row.findViewById(R.id.title);
             this.price = (TextView)this.row.findViewById(R.id.price);
             this.address = (TextView)this.row.findViewById(R.id.address);
             this.listingPhoto = (ImageView)this.row.findViewById(R.id.listingPhoto_img);
             this.listingRef = (TextView)this.row.findViewById(R.id.listingRef_lbl);
             this.isCommercial = (ToggleButton)this.row.findViewById(R.id.isCommercial_tggl);
         }

          void LoadValuesFrom(Listing listing){
        	  cityState.setText(listing.cityState);
        	  title.setText(listing.title);
        	  price.setText(listing.price);
        	  address.setText(listing.address);       	  
        	  listingRef.setText(listing.listingRef);
        	  isCommercial.setChecked(listing.isCommercial);
        	  
        	  if(listingPhoto != null && listing.primaryPhoto != ""){
        		  Picasso.with(getContext())
        		  .load(Konstant.TAG_LISTING_PHOTO_SEARCH_URL + listing.primaryPhoto)
        		  .placeholder(R.drawable.nosearchphoto)
        		  .resize(105, 105)
        		  .into(listingPhoto);    
        	  } 
          }
    }

    
    
    public void setCustomClickListener(boolean isAllLocSearch){
    	listView.setOnItemClickListener(this);
    	this.isAllLocSearch = isAllLocSearch;
    }
    
    
    public void setCustomScrollListener(){
    	listView.setOnScrollListener(this);
    }

@Override
public void onScroll(AbsListView view, int firstVisibleItem,
		int visibleItemCount, int totalItemCount) {

	
}

@Override
public void onScrollStateChanged(AbsListView view, int scrollState) {
	
	if( scrollState == SCROLL_STATE_IDLE){
		
		if(listView.getLastVisiblePosition() >= listView.getCount() - 1){	
		if (!AllSearchResult.isloadingMoreResult) {
			
			if (AllSearchResult.totalPgNo > AllSearchResult.currentResultIndex) {
				AllSearchResult.isloadingMoreResult = true;	
				((AllSearchResult)host).paginateResult();
			}			
			
			}
		}
	
	}
}
 

@Override
public void onItemClick(AdapterView<?> adapter, View view, int arg2, long arg3) {
	
	TextView listingRefLbl = (TextView)view.findViewById(id.listingRef_lbl);	
	ToggleButton isCommercialListingTgg  = (ToggleButton)view.findViewById(id.isCommercial_tggl);
	
	if (isAllLocSearch) {
	
		 AllSearchResult.isCommercialDetailListingLoad = isCommercialListingTgg.isChecked();
		 AllSearchResult.listingRef = listingRefLbl.getText();
	}
	else{
		LocationSearchResult.isCommercialDetailListingLoad = isCommercialListingTgg.isChecked();
		LocationSearchResult.listingRef = listingRefLbl.getText();
	}
		
	 detailsAjaxTask = new DetailsAjaxTask(progressGif,context,host, isAllLocSearch);	    
     detailsAjaxTask.execute(Konstant.TAG_LISTING_DETAIL_URL + listingRefLbl.getText());	
}  

}
