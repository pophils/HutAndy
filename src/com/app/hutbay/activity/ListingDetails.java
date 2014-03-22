package com.app.hutbay.activity;

import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.app.entity.ListingDetailViews;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.ViewHelper;

import Task.ContactAgentTask;
import Task.LoadDetailsTask;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ListingDetails extends SherlockFragmentActivity {
	 
	public static boolean isCommercialListing = true;
	private ListingDetailViews detailViews;
	private LinearLayout actionBtnLayout;
	private ViewPager viewPager;
	private ImageView ImageView;
	private Button mailFriendBtn;
	private Button contactAgentBtn;
	private Button callBtn;
	private Button smsBtn;
	private Button shareBtn;
	private Button requestDetails; 
	private EditText senderEmail;
	private EditText senderName;
	private EditText senderPhoneNo;
	private EditText message;
	private TextView statusMessage;
	private ContactAgentTask ajaxTask;	
	protected String agentNo;
	protected String link;
	protected String title;
	protected String smsMessage;
	private String listingRef;
	protected Dialog formDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		new ViewHelper().initConnectionCheck(this); // always perform this before loading any activity or fragment.
		
		super.onCreate(savedInstanceState);		         
		 Intent detailsIntent = getIntent();
         isCommercialListing = detailsIntent.getBooleanExtra(Konstant.TAG_IS_COMMERCIAL, true);
		
       if(isCommercialListing)
        	 setContentView(R.layout.activity_listing_details_commercial); 
         else
        	 setContentView(R.layout.activity_listing_details_residential); 
         
         detailViews = new ListingDetailViews(isCommercialListing, this);             		
         
         LoadDetailsTask detailsTask = new LoadDetailsTask(detailViews, getBaseContext(), detailsIntent,
        		                                           isCommercialListing, getSupportActionBar(), this);
         detailsTask.execute(detailsIntent.getStringExtra(Konstant.TAG_JSON_RESULT_XTRA));
         
         
         if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
        	 
        	    if(actionBtnLayout == null){					  
    				actionBtnLayout = (LinearLayout)findViewById(R.id.actionBtn_layt);    				
        	    }    	      
    	        
    	        LinearLayout.LayoutParams  params = (LinearLayout.LayoutParams) actionBtnLayout.getLayoutParams();    	        
    	        params.weight = 10;
	        	actionBtnLayout.setLayoutParams(params);	
	    }
         
         initDetailsBtns();  
         listingRef = detailsIntent.getStringExtra(Konstant.TAG_LISTING_REF_XTRA);
	}
	
  @Override
	 public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);	        
	    
	        if(actionBtnLayout == null){					  
				actionBtnLayout = (LinearLayout)findViewById(R.id.actionBtn_layt);
		    }
	        
	        if(viewPager == null){					
				viewPager = (ViewPager)findViewById(R.id.photopager);
				 ImageView = (ImageView)findViewById(id.detailImage);
	        }   	      
	        
	        LinearLayout.LayoutParams  params = (LinearLayout.LayoutParams) actionBtnLayout.getLayoutParams();
	        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){	        	
	        	params.weight = 10;	        	 
	        	ImageView.setScaleType(ScaleType.CENTER_INSIDE);
	        	  
	        }
	        else{
	        	  params.weight = 6;	        	         	   
		      	  ImageView.setScaleType(ScaleType.CENTER_CROP);		        	 
	        }
	        
	        actionBtnLayout.setLayoutParams(params);  
	        ImageView.setAdjustViewBounds(true);
	    }

	 public void setDetailBtnListener(){
		  
		   contactAgentBtn.setOnClickListener(new OnClickListener() {
			

				@Override
				public void onClick(View v) {						
					

					formDialog = new Dialog(ListingDetails.this);
					formDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					formDialog.setContentView(R.layout.contact_agent);
				    
					formDialog.setCancelable(true);
					formDialog.setCanceledOnTouchOutside(false);
					 
					 
					
					Window window = formDialog.getWindow();
					WindowManager.LayoutParams wlp = window.getAttributes();

					wlp.gravity = Gravity.CENTER;
					
					wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
					window.setAttributes(wlp);
					window.setBackgroundDrawable(getResources().getDrawable(R.drawable.form_dialog_bg));
					
					Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
					 
					
					 Point screenSize = new Point();
			         display.getSize(screenSize);
			         int width = screenSize.x;
			       
					window.setLayout((int)(width - (width*.03)), LayoutParams.WRAP_CONTENT);
				
				 
					formDialog.show();
					
					
					requestDetails = (Button) formDialog.findViewById(id.requestDetails);
				    senderEmail = (EditText) formDialog.findViewById(id.senderEmail);
				    senderName = (EditText) formDialog.findViewById(id.senderName);
					senderPhoneNo = (EditText) formDialog.findViewById(id.senderPhoneNo);
					message = (EditText) formDialog.findViewById(id.message);
					statusMessage = (TextView)formDialog.findViewById(id.statusMessage);
					Button cancel = (Button) formDialog.findViewById(id.cancel);
					
					
					requestDetails.setOnClickListener(new View.OnClickListener() {						
						

						@Override
						public void onClick(View arg0) {						  
			            	   
			            	   statusMessage.setVisibility(View.GONE);
								


								if( senderPhoneNo.getText().toString() != null
									 && senderPhoneNo.getText().toString().trim().length() > 0 &&
									 !ViewHelper.validatePhone(senderPhoneNo.getText().toString())){
									
									   statusMessage.setText("Please enter a valid phone number.");
					            	   statusMessage.setTextColor(Color.RED);
					            	   statusMessage.setVisibility(View.VISIBLE);   
								}
								else if( !ViewHelper.validateEmail(senderEmail.getText().toString())){
									
									   statusMessage.setText("Please enter a valid email address.");
					            	   statusMessage.setTextColor(Color.RED);
					            	   statusMessage.setVisibility(View.VISIBLE);   
								}
								else if(message.getText().toString() == null || message.getText().toString().trim().length() < 1){
									
									 statusMessage.setText("Please enter a brief message.");
					            	   statusMessage.setTextColor(Color.RED);
					            	   statusMessage.setVisibility(View.VISIBLE);   
								}
								else if(message.getText().toString() != null && message.getText().toString().trim().length() > 200){
									
									   statusMessage.setText("Your message must be no more than 200 characters.");
					            	   statusMessage.setTextColor(Color.RED);
					            	   statusMessage.setVisibility(View.VISIBLE);   
								}
								else{
									if(ajaxTask == null)							 
										ajaxTask = new ContactAgentTask(getBaseContext(), ListingDetails.this, requestDetails);
									ajaxTask.execute(Konstant.TAG_REQUEST_DETAILS_URL, listingRef, senderEmail.getText().toString(), senderName.getText().toString(),
									senderPhoneNo.getText().toString(), message.getText().toString());
								}
							}
					});				
					 
					cancel.setOnClickListener(new View.OnClickListener() {						
						

						@Override
						public void onClick(View arg0) {
					
								if(ajaxTask != null)							 
								ajaxTask.cancel(true);
								
								disposeTask();
							    senderName.setText("");
				                senderEmail.setText("");
				                message.setText("");
				                senderPhoneNo.setText("");  
				                requestDetails.setText("Send");
				                statusMessage.setVisibility(View.GONE);
				                formDialog.cancel();
						}
					});
	
				}
			});
		   
		   
		   callBtn.setOnClickListener(new OnClickListener() {		
			 
				@Override
				public void onClick(View v) {				
					 setCallAgentListener(detailViews.agentPhoneNo.getText());					 
				}
			});
		   
		   
		   mailFriendBtn.setOnClickListener(new OnClickListener() {		
				 
				@Override
				public void onClick(View v) {				
					 setMailfriendListener(detailViews.listingWebUrl.getText());				 
				}
			});
		   
		   
		   shareBtn.setOnClickListener(new OnClickListener() {		
				 
				@Override
				public void onClick(View v) {				
					 setShareListingListener(detailViews.listingWebUrl.getText());				 
				}
			});
		   
		   smsBtn.setOnClickListener(new OnClickListener() {		
				 
				@Override
				public void onClick(View v) {				
					setSmsListingListener(detailViews.listingWebUrl.getText());				 
				}
			});
	 
		   detailViews.agentPhoneNo.setOnClickListener(new OnClickListener() {		
				 
				@Override
				public void onClick(View v) {				
					 setCallAgentListener(detailViews.agentPhoneNo.getText());					 
				}
			});
	
	  }

	 private void setMailfriendListener(CharSequence link){
		  String  subject = "Property Recommendation";
		  String message = "Hi friend, \n You might want to check "
	           		+ "out this property on hutbay with the link below, \n "
           	        + link;
		  
		  
		  Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
			 String uriText = "mailto:" + Uri.encode("") + 
			           "?subject=" + Uri.encode(subject) + 
			           "&body=" + Uri.encode(message);
			 Uri uri = Uri.parse(uriText);

			 mailIntent.setData(uri);
			 mailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(mailIntent, "Mail property to friend..."));
			
			// ViewHelper.mailTo(getBaseContext(), "", subject, message, "Mail property to friend...");
		 }
		 
	 private void setCallAgentListener(CharSequence phoneNo){
		 
		 ViewHelper.callOut(getBaseContext(), phoneNo.toString());
     }
		      
	 private void setSmsListingListener(CharSequence link){
			 			   
			  String message = "Hi, \nYou might want to check "
		           		+ "out this property on hutbay via the link below, \n "
	           	        + link;
			  ViewHelper.sendSms(getBaseContext(), "", message);
     }
		 
	 private void setShareListingListener(CharSequence link){
	 
	     Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		 intent.setType("text/plain");
		 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);		 
		 intent.putExtra(Intent.EXTRA_SUBJECT,  "Property Recommendation");
		 intent.putExtra(Intent.EXTRA_TEXT, link);
		 
		 startActivity(Intent.createChooser(intent, "How do you want to share this property"));
	 
	 }
  
	 private void initDetailsBtns(){		
		 
	   contactAgentBtn = (Button)findViewById(id.contactagent_btn);	  
	   callBtn = (Button)findViewById(id.callBtn);
	   smsBtn = (Button)findViewById(id.smsBtn);
	   shareBtn = (Button)findViewById(id.shareBtn);
	   mailFriendBtn = (Button)findViewById(id.mailfriend_btn);
	}

	 public void disposeTask() {
		ajaxTask = null;
	}

	 public void processResult(String result) {
		 
		   try{
            
               JSONObject jsonObject = new JSONObject(result);

               if(jsonObject.getBoolean("Success")){
            	   
            	   statusMessage.setText("Your message has been sent to the agent.");
            	   statusMessage.setTextColor(Color.parseColor("#076107"));
            	   statusMessage.setVisibility(View.VISIBLE);
                   senderName.setText("");
                   senderEmail.setText("");
                   message.setText("");
                   senderPhoneNo.setText("");                    
               }
               else{
            	   statusMessage.setText(jsonObject.getString("Message"));
            	   statusMessage.setTextColor(Color.RED);
            	   statusMessage.setVisibility(View.VISIBLE);            	           	                   
               }         
              
           }
		   catch (Exception e){
               Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();              
           }
		   
		   disposeTask();
	}

}
