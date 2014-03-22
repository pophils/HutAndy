package com.app.hutbay.fragment;

import java.util.Map;

import com.actionbarsherlock.app.SherlockFragment;
import com.app.hutbay.Landing;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.activity.ListingDetails;
import com.app.hutbay.adapter.SpinnerAdapter;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.ViewHelper;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class FilterSearch extends SherlockFragment {
	private Button forsaleBtn;
	private Button torentBtn;
	private Button searchBtn;
	private Button resetBtn;
	private EditText q;
	private TextView maxPriceTV;
	 
	private CheckBox isNewHome;
	private boolean isForsaleButtonClicked = true;
	private Spinner propertyTypeSpinner;
	private Spinner bedSpinner;
	private Spinner bathSpinner;
	private Spinner dateListedSpinner;
	private Spinner sortTypeSpinner;
	private SeekBar maxPriceSeekBar;
	private Activity host;
	
	public String bed;
	public String bath;
	public String dateListed;
	public String sortType;
	public String propertyType;
	public int price;
	public boolean showNewListing;
	private static Map<String, String> listingTypeDict;
	
	@Override
	public void onAttach(Activity activity) {		 
		super.onAttach(activity);
		this.host = activity;
	}	
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {	     
	        return inflater.inflate(R.layout.frag_filter_search, container, false);
	    }
	 
	 @Override
		public void onCreate(Bundle savedInstanceState) {			
			 super.onCreate(savedInstanceState);		   	  
		}
		
	 @SuppressWarnings("deprecation")
	@Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        setRetainInstance(true);			
		     initButtonTabs(savedInstanceState);	
		     initSpinner();
		     q = (EditText)host.findViewById(id.q_txt);		     
		    
		     listingTypeDict = ViewHelper.initListingTypeDict();
		     
		     setIsNewHomeCheckEvent();
		     setActionButtonListener();
		     maxPriceSeekBar = (SeekBar)host.findViewById(id.maxPrice_seekBar);
		     maxPriceTV = (TextView)host.findViewById(id.maxPrice_txt);
		     setPriceProgressListener();
	    }
		 
	   @Override
	   public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        
	     /*   if(footerBtnLay == null)
	        footerBtnLay = (LinearLayout)host.findViewById(R.id.footerBtnLay);
	        
	        LinearLayout.LayoutParams  params = (LinearLayout.LayoutParams) footerBtnLay.getLayoutParams();
	        if(newConfig.orientation ==  Configuration.ORIENTATION_LANDSCAPE){
	        	
	        	params.weight = 27;
	            footerBtnLay.setLayoutParams(params);
	        }
	        else{
	        	  params.weight = 10;
		          footerBtnLay.setLayoutParams(params);
	        }*/
	    }
	   
	  
	
	   @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putBoolean("isForsaleButtonClickedKey", isForsaleButtonClicked);
	    }
	 
	   
	   private void setSpinnerItemSelectedSpinner()
	   {
		   propertyTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {	
				
					try{
						propertyType = listingTypeDict.get(parent.getItemAtPosition(position).toString());
						
						if(propertyType == null || propertyType == "")
							propertyType = "0";						
						
					}					
					catch(Exception exc){						
						propertyType = "0";
					}
					
					//if property is a commercial type, disable bed and bath
					if(propertyType == "14" || propertyType == "10" || propertyType == "11" || propertyType == "12" ){
						bedSpinner.setEnabled(false);
						bathSpinner.setEnabled(false);
						bedSpinner.setSelection(0);
						bathSpinner.setSelection(0);
					}
					else{
						bedSpinner.setEnabled(true);
						bathSpinner.setEnabled(true);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {					 
					
				}
			});	     
		     
		   
		    bedSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {					
					bed = String.valueOf(position);							 
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
				}
			});
		   
		    bathSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    	@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {	
				 
					bath  = String.valueOf(position);					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
				}
			});
		   
		   
		    dateListedSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    	@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {					 
					dateListed  = String.valueOf(position);					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
				}
			});
		   
		    sortTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    	@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {	
				 
					sortType  = String.valueOf(position);					
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
				}
			});
	   }
	   
	   
	   private void initSpinner(){
		   
		    propertyTypeSpinner = (Spinner) host.findViewById(R.id.propertyType_spinner);
		    bedSpinner = (Spinner) host.findViewById(R.id.bed_spinner);
		    bathSpinner = (Spinner) host.findViewById(R.id.bath_spinner);
		    dateListedSpinner = (Spinner) host.findViewById(R.id.dateListed_spinner);
		    sortTypeSpinner = (Spinner) host.findViewById(R.id.sortType_spinner);
		    
		    SpinnerAdapter propertyTypeAdapter = new  SpinnerAdapter(host, propertyTypeSpinner,R.array.property_type_array, android.R.layout.simple_spinner_item);
		    propertyTypeAdapter.LoadContent();
		    		    
		    SpinnerAdapter bedAdapter = new  SpinnerAdapter(host, bedSpinner,R.array.bed_array, android.R.layout.simple_spinner_item);
		    bedAdapter.LoadContent();
		    
		    SpinnerAdapter bathAdapter = new  SpinnerAdapter(host, bathSpinner,R.array.bath_array, android.R.layout.simple_spinner_item);
		    bathAdapter.LoadContent();
		   
		    SpinnerAdapter dateListedAdapter = new  SpinnerAdapter(host, dateListedSpinner,R.array.datelisted_array, android.R.layout.simple_spinner_item);
		    dateListedAdapter.LoadContent();
		     
		    SpinnerAdapter sortTypeAdapter = new  SpinnerAdapter(host, sortTypeSpinner,R.array.sort_type_array, android.R.layout.simple_spinner_item);
		    sortTypeAdapter.LoadContent();

		    setSpinnerItemSelectedSpinner();		    
	}
	
    	private void initButtonTabs(Bundle savedInstanceState){
		 forsaleBtn = (Button)host.findViewById(R.id.forsaleBtn);
		 torentBtn = (Button)host.findViewById(R.id.torentBtn);
		
		 if (savedInstanceState == null) {
			 toggleAdvertTypeTab(isForsaleButtonClicked);
		 }
		 else{
			 isForsaleButtonClicked = savedInstanceState.getBoolean("isForsaleButtonClickedKey");				 
			 toggleAdvertTypeTab(isForsaleButtonClicked);				 		 
		 }		 
			 
		forsaleBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				isForsaleButtonClicked = true;
				toggleAdvertTypeTab(isForsaleButtonClicked);					 			
			}
		});
		
		torentBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				 isForsaleButtonClicked = false;
				 toggleAdvertTypeTab(isForsaleButtonClicked);
			}
		});
	}

        @SuppressWarnings("deprecation")
		private void toggleAdvertTypeTab(boolean isForSaleTab){
    
    	if(isForSaleTab){
    		forsaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_filter_search_tab_pressed));
			 torentBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_filter_search_tab));
			 forsaleBtn.setTextColor(getResources().getColor(R.color.custom_button_tab_pressed_text));
			 torentBtn.setTextColor(getResources().getColor(R.color.custom_filter_search_color));
			 isForsaleButtonClicked = true;
    	}
    	else{
    		 torentBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_filter_search_tab_pressed));
			 forsaleBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_filter_search_tab));
			 torentBtn.setTextColor(getResources().getColor(R.color.custom_button_tab_pressed_text));
			 forsaleBtn.setTextColor(getResources().getColor(R.color.custom_filter_search_color)); 
			 isForsaleButtonClicked = false;
    	}
    }
    
        private void setActionButtonListener(){
        	
        	if (searchBtn == null) {
        		searchBtn = (Button)host.findViewById(id.search_btn);
			}
        	
        	if (resetBtn == null) {
        		resetBtn = (Button)host.findViewById(id.reset_btn);
			}
        	
        	setSearchBtnListener();
        	setResetBtnListener();        	
        }
        
        private void setIsNewHomeCheckEvent() {
			
        	if(isNewHome == null){
        		isNewHome = (CheckBox)host.findViewById(id.isNewHome_chkbox);
        	}
        	
        	isNewHome.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                      if(isChecked)
                    	  showNewListing = true;
                      else {
						showNewListing = false;
				      }	
                }
			});
		}

        private void setSearchBtnListener() {
			
        	searchBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				 
					String q = FilterSearch.this.q.getText().toString().trim();
					
				if(q.length() < 1){
					Toast.makeText(host, "Please enter a location", Toast.LENGTH_LONG).show();
					//set focus on search editText
				}
				else {
					
					String qString = "maxprice="+price+"&bed="+bed+"&bath="+bath+"&sortType="+sortType+"&listingType="+propertyType+"&dh="+dateListed
							+"&pgSize=20&q="+q+"&sortType="+ sortType;
						
					if(isForsaleButtonClicked){
						LocationSearch.isForSaleSearch = true;
						qString = qString + "&advertType=1";
					}
						
					else {
						LocationSearch.isForSaleSearch = false;
						qString = qString + "&advertType=2";
					}						
					
					if (showNewListing)
						qString = qString + "&isnewhome=1";
					else
						qString = qString + "&isnewhome=0";
					
					LocationSearch locationSearchFrag = new LocationSearch();
					LocationSearch.searchUrl = Konstant.TAG_REFINE_SEARCH_URL + qString;
					
					 ((FragmentActivity) host).getSupportFragmentManager()
             		  .beginTransaction()
             		  .add(locationSearchFrag, "locationSearchFrag")
             		  .commit();  
				}
			}
			});
		}

        
    private void setResetBtnListener() {
			
        	resetBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				 
					q.setText("");
					propertyTypeSpinner.setSelection(0);
					bedSpinner.setSelection(0);
					bathSpinner.setSelection(0);
					dateListedSpinner.setSelection(0);
					sortTypeSpinner.setSelection(0);
					isNewHome.setChecked(false);
					maxPriceSeekBar.setProgress(0);
					
				}
			});
		}
    
    
    private void setPriceProgressListener() {
		
     maxPriceSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
						maxPriceSeekBar.setSecondaryProgress(seekBar.getProgress());
						
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			
			 if (progress <= 1) 
				 maxPriceTV.setText(" Below ₦1M");
			 
			 else if (progress <= 90) 
				 maxPriceTV.setText(" Below ₦" + progress + "M");
				
			 else if (progress <= 95) 
				 maxPriceTV.setText(" Below ₦100M");
			 else 
				 maxPriceTV.setText(" Above ₦100M");	
			 price = progress;
		}
	});
	}

}
