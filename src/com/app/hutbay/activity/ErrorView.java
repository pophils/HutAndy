package com.app.hutbay.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.utility.NetworkManager;
import com.app.hutbay.utility.ViewHelper;

import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorView extends SherlockActivity {

	
	public static String ErrorTitleExtra = "Error";
	public static String errorMessageExtra = "Error";
	private TextView errorMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		 ViewHelper.ChangeActionBarBackgroundColor(getBaseContext(), getSupportActionBar());
		 
		 Intent errorIntent = getIntent();
		 ViewHelper.ChangeActionBarTitle(getBaseContext(), getSupportActionBar(), errorIntent.getStringExtra(ErrorTitleExtra));
		 setContentView(R.layout.activity_error_view);
		 
		 
		 errorMessage = (TextView)findViewById(id.ErrorMessage_lbl);
		 errorMessage.setText(errorIntent.getStringExtra(errorMessageExtra));	
		 
	}		
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {		 
        
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	if(!NetworkManager.isConnectionAvailable(getBaseContext())){
        		Toast.makeText(getBaseContext(), "Your device appears not to be connected to the internet. Please check your connection", Toast.LENGTH_SHORT) // display alert dialog later
        		.show();
        		
        		 Intent loadHomeActivity = new Intent(this, ErrorView.class);  
        		 loadHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	     startActivity(loadHomeActivity);
        		 return true;
        	}	  
        }
        
        return super.onKeyDown(keyCode, event);       
    }

}
