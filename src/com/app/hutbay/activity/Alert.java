package com.app.hutbay.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.adapter.SpinnerAdapter;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.ViewHelper;

import Task.LoadCityAjaxTask;
import Task.SaveAlertTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Alert extends SherlockActivity {

	private Button forsaleBtn;
	private Button torentBtn;
	private Button saveBtn;
	private Button resetBtn;
	private EditText emailTv;
	private TextView maxPriceTV;	
	private boolean isForsaleButtonClicked = true;
	private Spinner propertyTypeSpinner;
	private Spinner bedSpinner;
	private Spinner stateSpinner;
	private Spinner citySpinner;
	private Spinner notificationSpinner;
	private SeekBar maxPriceSeekBar;	
	public String bed;
	public String cityId = "0";
	public String stateId = "0";
	public String notificationId;
	public String propertyType;
	public int price;
	private SpinnerAdapter cityAdapter;
	protected String email;
	protected SaveAlertTask ajaxTask;
	protected LoadCityAjaxTask cityAjaxTask;
	private JSONArray cityArray;
	private ArrayList<CharSequence> cityList;
	private static Map<String, String> listingTypeDict;
	private static Map<String, String> cityDictionary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_alert);
		ViewHelper.ChangeActionBarBackgroundColor(getBaseContext(), getSupportActionBar());	
		initButtonTabs(savedInstanceState);	
	    initSpinner();
	     
	     listingTypeDict = ViewHelper.initAlertListingTypeDict();
	     emailTv = (EditText)findViewById(id.email_txt);
	     
	     setActionButtonListener();
	     maxPriceSeekBar = (SeekBar)findViewById(id.maxPrice_seekBar);
	     maxPriceTV = (TextView)findViewById(id.maxPrice_txt);
	     setPriceProgressListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		 
		return true;
	}
		
    private void initSpinner(){
		   
		    propertyTypeSpinner = (Spinner) findViewById(R.id.propertyType_spinner);
		    bedSpinner = (Spinner) findViewById(R.id.bed_spinner);
		    stateSpinner = (Spinner) findViewById(R.id.state_spinner);
		    citySpinner = (Spinner) findViewById(R.id.city_spinner);
		    notificationSpinner = (Spinner) findViewById(R.id.notification_spinner);
		    
		    SpinnerAdapter propertyTypeAdapter = new  SpinnerAdapter(this, propertyTypeSpinner,R.array.alert_property_type_array, android.R.layout.simple_spinner_item);
		    propertyTypeAdapter.LoadContent();
		    		    
		    SpinnerAdapter bedAdapter = new  SpinnerAdapter(this, bedSpinner,R.array.bed_array, android.R.layout.simple_spinner_item);
		    bedAdapter.LoadContent();
		    
		    SpinnerAdapter stateAdapter = new  SpinnerAdapter(this, stateSpinner,R.array.state_array, android.R.layout.simple_spinner_item);
		    stateAdapter.LoadContent();
		   
		    cityAdapter = new  SpinnerAdapter(this, citySpinner,R.array.city_array, android.R.layout.simple_spinner_item);
		    cityList = new ArrayList<CharSequence>();
		    cityAdapter.LoadContent(cityList );
		     
		    SpinnerAdapter notificationAdapter = new  SpinnerAdapter(this, notificationSpinner,R.array.alert_notification_array, android.R.layout.simple_spinner_item);
		    notificationAdapter.LoadContent();

		    setSpinnerItemSelectedSpinner();		    
	}
	
   	private void initButtonTabs(Bundle savedInstanceState){
		
   		forsaleBtn = (Button)findViewById(R.id.forsaleBtn);
		 torentBtn = (Button)findViewById(R.id.torentBtn);
		
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

    private void setSpinnerItemSelectedSpinner(){
    	
		   propertyTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {	
				
					try{
						propertyType = listingTypeDict.get(parent.getItemAtPosition(position).toString());
						
						if(propertyType == null || propertyType == "")
							propertyType = "-1";						
						
					}					
					catch(Exception exc){						
						propertyType = "-1";
					}
					
					//if property is a commercial type, disable bed and bath
					if(propertyType == "14" || propertyType == "10" || propertyType == "11" || propertyType == "12" ){
						bedSpinner.setEnabled(false);						
						bedSpinner.setSelection(0);						
					}
					else{
						bedSpinner.setEnabled(true);						
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
		   
		    stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    	@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {	
				 
					stateId  = String.valueOf(position);	
					
					if(position > 0){
						if(cityAjaxTask == null)
							cityAjaxTask = new LoadCityAjaxTask(getBaseContext(), Alert.this);
						
						cityList.clear();				
						cityAdapter.notifyDataSetChanged();
						cityList.add("Loading cities...");
						cityAdapter.notifyDataSetChanged();
						
						cityAjaxTask.execute(Konstant.TAG_GET_CITIES_URL + stateId);
					}
					else{
						cityList.clear();				
						cityAdapter.notifyDataSetChanged();
					}
					 
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
				}
			});
		 
		    citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    	@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {					 
					cityId  = String.valueOf(position);	
		    		
		    		if(position > 0)
					   cityId = cityDictionary.get(parent.getItemAtPosition(position).toString()); 		 
		    			
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
				}
			});
		   
		    notificationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    	@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {	
				 
					notificationId  = String.valueOf(position);					
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					 
					
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
    	
    	if (saveBtn == null) {
    		saveBtn = (Button)findViewById(id.save_btn);
		}
    	
    	if (resetBtn == null) {
    		resetBtn = (Button)findViewById(id.reset_btn);
		}
    	
    	setSaveBtnListener();
    	setResetBtnListener();        	
    }

    private void setSaveBtnListener() {
		
    	saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				email = emailTv.getText().toString();
				
				
			  if(email.trim().length() < 1){
					Toast.makeText(Alert.this, "Please enter your email address", Toast.LENGTH_LONG).show();
					 
				}
				
				else if(!ViewHelper.validateEmail(email)){
					Toast.makeText(Alert.this, "Please enter a valid email", Toast.LENGTH_LONG).show();					 
				}
				
				else if(propertyType == "-1"){
					Toast.makeText(Alert.this, "Please set your property preference", Toast.LENGTH_LONG).show();					 
				}
				else if(stateSpinner.getSelectedItemPosition() == 0){
					Toast.makeText(Alert.this, "Please select a state", Toast.LENGTH_LONG).show();					 
				}
				
				else if(citySpinner.getSelectedItemPosition() == 0){
					Toast.makeText(Alert.this, "Please select a city", Toast.LENGTH_LONG).show();					 
				}
			  
				else if(cityId == "0"){
					Toast.makeText(Alert.this, "Please select a city", Toast.LENGTH_LONG).show();					 
				}				
				else{
					
					if(ajaxTask == null)							 
						ajaxTask = new SaveAlertTask(getBaseContext(), Alert.this, saveBtn);
					if(isForsaleButtonClicked)
					ajaxTask.execute(Konstant.TAG_SAVE_ALERT_URL, Konstant.TAG_FOR_SALE,
							email, propertyType, stateId, cityId, bed, price+"", notificationId);
					else
						ajaxTask.execute(Konstant.TAG_SAVE_ALERT_URL, Konstant.TAG_TO_RENT,
								email, propertyType, stateId, cityId, bed, price +"", notificationId);
				}
			 
		
		}
		});
	}
 
    private void setResetBtnListener() {
		
    	resetBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {			 
				emailTv.setText("");
				propertyTypeSpinner.setSelection(0);
				bedSpinner.setSelection(0);
				stateSpinner.setSelection(0);				 				 
				notificationSpinner.setSelection(0);				 
				maxPriceSeekBar.setProgress(0);	
				disposeTask();
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

	public void disposeTask() {
		ajaxTask = null;
		cityAjaxTask = null;
		cityList.clear();				
		cityAdapter.notifyDataSetChanged(); 
	}

	public void processResult(String result) {
		 
		   try{
            
               JSONObject jsonObject = new JSONObject(result);

               if(jsonObject.getBoolean("Success")){            	   
            	   
            	   ViewHelper.showGenericDialog(this, "Alert saved", "Your Alert  has been saved.", "Ok");
            	   resetBtn.performClick();               
               }
               else{            	          
            	   ViewHelper.showGenericDialog(getBaseContext(), "Alert Error", jsonObject.getString("Message"), "Retry");
            	   
               }           
             
           }
		   catch (Exception e){
               Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();              
           }
		   
		   ajaxTask = null;
	}

	public void processCityResult(String jsonResult) {
		
		cityDictionary = new HashMap<String, String>();
		
		cityList.clear();				
		cityAdapter.notifyDataSetChanged(); 
		
		try{
			 JSONObject jsonObject = new JSONObject(jsonResult);
			 cityArray = jsonObject.getJSONArray(Konstant.TAG_CITIES);
			  
			 if(cityArray.length() > 0){
				 cityList.add("Select a city...");	
					cityAdapter.notifyDataSetChanged(); 
					 for (int pos = 0; pos < cityArray.length(); pos++) {
							JSONObject cityObj = cityArray.getJSONObject(pos);	
							String cityName = cityObj.getString("Name");
							cityDictionary.put(cityName, cityObj.getString("Id"));
							
							cityList.add(cityName);
							cityAdapter.notifyDataSetChanged();						 
						}
			 }
		 cityAjaxTask = null;
		}
		catch(Exception ex){
			
		}	 
		
	}

	public void showErrorDialog(String message) {
		ViewHelper.showGenericDialog(this, 
				"Error", 
				message,
				"Retry"
				);  
		}
}
