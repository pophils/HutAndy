package com.app.hutbay.activity;

import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.utility.Konstant;
import com.app.hutbay.utility.ViewHelper;
import Task.SendFeedbackTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBack extends SherlockActivity {

	private EditText senderName;
	private EditText senderEmail;
	private EditText message;
	private EditText senderPhoneNo;
	private Button sendFeedback;
	private SendFeedbackTask ajaxTask;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_feedback);
		ViewHelper.ChangeActionBarBackgroundColor(getBaseContext(), getSupportActionBar());	
		
		 senderEmail = (EditText)findViewById(id.senderEmail);
		    senderName = (EditText)findViewById(id.senderName);
			senderPhoneNo = (EditText)findViewById(id.senderPhoneNo);
			message = (EditText)findViewById(id.message);
			sendFeedback = (Button)findViewById(id.sendFeedback);
			
			
			sendFeedback.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {	            	   
	            	
						if( senderPhoneNo.getText().toString() != null
							 && senderPhoneNo.getText().toString().trim().length() > 0 &&
							 !ViewHelper.validatePhone(senderPhoneNo.getText().toString())){
							
							showErrorMessage("Please enter a valid phone number.");							   
						}
						else if( !ViewHelper.validateEmail(senderEmail.getText().toString())){
							showErrorMessage("Please enter a valid email address.");								 
						}
						else if(message.getText().toString() == null || message.getText().toString().trim().length() < 1){
							
							showErrorMessage("Please enter a brief message.");							 
						}
						else if(message.getText().toString() != null && message.getText().toString().trim().length() > 1500){
							showErrorMessage("Your message must be no more than 200 characters.");							   
						}
						else{
							if(ajaxTask == null)							 
								ajaxTask = new SendFeedbackTask(getBaseContext(), FeedBack.this, sendFeedback);
							ajaxTask.execute(Konstant.TAG_SAVE_FEEDBACK_URL,  
									senderEmail.getText().toString(),									
							senderPhoneNo.getText().toString(),
							message.getText().toString(),
							senderName.getText().toString());
						}
					}
			});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		return true;
	}

	public void disposeTask() {
		ajaxTask = null;		
	}

	public void processResult(String jsonResult) {
		   try{
	            
               JSONObject jsonObject = new JSONObject(jsonResult);

               if(jsonObject.getBoolean("Success")){            	   
            	 
                   senderName.setText("");
                   senderEmail.setText("");
                   message.setText("");
                   senderPhoneNo.setText("");   
                   
                   ViewHelper.showGenericDialog(this, 
           				"Feeback Sent", 
           				"Thanks, we've got your feedback.",
           				"Ok"
           				);  
               }
               else{
            	   
            	   showErrorMessage(jsonObject.getString("Message"));            	         	           	                   
               }         
              
           }
		   catch (Exception e){
               Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();              
           }
		   
		   disposeTask();
	}

	public void showErrorMessage(String message) {
		ViewHelper.showGenericDialog(this, 
				"Error", 
				message,
				"Retry"
				);  
	}

}
