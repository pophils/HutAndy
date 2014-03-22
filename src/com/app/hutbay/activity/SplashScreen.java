package com.app.hutbay.activity;

import com.app.hutbay.Landing;
import com.app.hutbay.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;

public class SplashScreen extends Activity {

	
	 // Splash screen timer (Splashscreen is displaced after 2000sec.)
    private static int SPLASH_TIME_OUT = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		
		   new Handler().postDelayed(new Runnable() {			  
	 
	            @Override
	            public void run() {
	               
	                Intent i = new Intent(SplashScreen.this, Landing.class);
	                startActivity(i);	              
	                finish();
	            }
	            
	        }, SPLASH_TIME_OUT);
	}

	
	
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	       
	        if(keyCode == KeyEvent.KEYCODE_BACK){
	        	        	
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    } 
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		 
		return true;
	}

}
