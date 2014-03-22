package com.app.hutbay.fragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.utility.Konstant;
import com.squareup.picasso.Picasso;

import Task.LoadPhotoTask;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailPhoto extends SherlockFragment {

	
	private Activity host; 
	public static Dialog loadingGifDialog;
	public static String searchUrl; 
	private ImageView imageContainer;
	public LoadPhotoTask loadPhotoTask;
	public String photoPath;
	
	
	
	 public DetailPhoto(String photoPath) {
		this.photoPath = photoPath;
		 // TODO Auto-generated constructor stub
	} 
	 
	@Override
	public void onAttach(Activity activity) {
		 super.onAttach(activity);
		 this.host = activity;
	}
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRetainInstance(true);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {	
	    	
	       return inflater.inflate(R.layout.frag_details_photo, container, false);
	    }
	    
	    
	 /*  @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		    imageContainer = (ImageView)host.findViewById(id.detailPhotoContainer);
		    
		  Picasso.with(host) 
	   		  .load(Konstant.TAG_DETAILS_PHOTO_URL + photoPath)      		  
	   	      .resize(250, 190)
	   		  .into(imageContainer); 
		    
	   //     loadPhotoTask = new LoadPhotoTask(imageContainer, host);
	      //  loadPhotoTask.execute(photoPath);	  
	}*//*
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        setRetainInstance(true);	
	        
	        if (savedInstanceState == null) {
				
	            Toast.makeText(host, photoPath, Toast.LENGTH_SHORT) // display alert dialog later
	  			.show();
	  	        
	  	        imageContainer = (ImageView)host.findViewById(id.detailPhotoContainer);
	  	        loadPhotoTask = new LoadPhotoTask(imageContainer, host);
	  	        loadPhotoTask.execute(photoPath);	  
			}
	        
	    }*/
	    
}
