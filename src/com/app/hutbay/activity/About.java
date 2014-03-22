package com.app.hutbay.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.utility.ViewHelper;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class About extends SherlockActivity {

	private TextView contactEmail;
	private TextView blog;
	private TextView privacy;
	private TextView terms;
	private TextView landingUrl;
	private ImageButton backIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_about);
	    
		initViews();
		setcustomClickListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		 
		return true;
	}
	
	
	private void initViews(){		
		 
		   contactEmail = (TextView)findViewById(id.contactEmail);	  
		   blog = (TextView)findViewById(id.blog);
		   privacy = (TextView)findViewById(id.privacy);
		   terms = (TextView)findViewById(id.termofuse);
		   landingUrl = (TextView)findViewById(id.landingUrl);
		   backIcon = (ImageButton)findViewById(id.backBtn);
		}

	private void setcustomClickListeners(){		
		 
		   contactEmail = (TextView)findViewById(id.contactEmail);	  
		   blog = (TextView)findViewById(id.blog);
		   privacy = (TextView)findViewById(id.privacy);
		   terms = (TextView)findViewById(id.termofuse);
		   landingUrl = (TextView)findViewById(id.landingUrl);
		   backIcon = (ImageButton)findViewById(id.backBtn);
		
		   contactEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 
				  Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
					 String uriText = "mailto:" + Uri.encode("contact@hutbay.com") + 
					           "?subject=" + Uri.encode("Need Help") + 
					           "&body=" + Uri.encode("");
					 Uri uri = Uri.parse(uriText);

					 mailIntent.setData(uri);
					 mailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(mailIntent, "Drop us a note..."));
			}
		});
		   		   
		   blog.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				  ViewHelper.browse(getBaseContext(), "http://hutbayblog.com");
				}
			});
		   
		   privacy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ViewHelper.browse(getBaseContext(), "http://hutbay.com/privacy");					
				}
			});
		   
		   terms.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				 
					ViewHelper.browse(getBaseContext(), "http://hutbay.com/tos");
				}
			});		   
		   
		   
		   landingUrl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				 
					ViewHelper.browse(getBaseContext(), "http://hutbay.com?m=0");
				}
			});
		   
		   backIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				 
					
					// ViewHelper.letsGoHome(getBaseContext());
					onBackPressed();
				}
			});
		   
		}
 
}
