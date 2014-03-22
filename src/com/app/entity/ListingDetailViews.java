package com.app.entity;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.app.hutbay.R;
import com.app.hutbay.R.id;
import com.app.hutbay.activity.ListingDetails;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

 
public class ListingDetailViews {
	
	public TextView price;
	public TextView priceTitle;
	public TextView address;
	public TextView parkingType;
	public TextView sqft;
	public ImageView detailPhoto;
	
	public TextView propertyType;
	public TextView status;
	public TextView fullAddress;
	public TextView yearBuilt;
	
	public TextView dateAdded;
	public TextView agentName;
	public TextView agentPhoneNo;
	public ImageView agentPhoto;
	public ImageView companyLogo;
	public ImageView detailImage;
	
	public TextView companyName;
	public TextView description;
	public TextView title;
	public TextView numOfViews;
	
	
	public TextView bed;
	public TextView listingWebUrl;
	public TextView bath; 
	public ViewPager photosViewPager;
	
	private ListingDetails context;
	
	public ListingDetailViews(boolean isCommercial, SherlockFragmentActivity activity ) {
		
		context = (ListingDetails) activity;
		price = (TextView)context.findViewById(id.price);
		priceTitle = (TextView)context.findViewById(id.priceTitle);
		title = (TextView)context.findViewById(id.title);
		address = (TextView)context.findViewById(id.address);
		
		status = (TextView)context.findViewById(id.status);
		fullAddress = (TextView)context.findViewById(id.fulladdress);
		propertyType = (TextView)context.findViewById(id.propertyType);
		description = (TextView)context.findViewById(id.description);
		
		dateAdded = (TextView)context.findViewById(id.dateAdded);
		yearBuilt = (TextView)context.findViewById(id.yearBuilt);
		numOfViews = (TextView)context.findViewById(id.totalView);
		
		listingWebUrl = (TextView)context.findViewById(id.listingWebUrl);
		
		agentName = (TextView)context.findViewById(id.agentName_lbl);
		companyName = (TextView)context.findViewById(id.companyName);
		agentPhoneNo = (TextView)context.findViewById(id.agentPhone_lbl);	
	    agentPhoto = (ImageView)context.findViewById(id.agentPhoto_img);
	    detailPhoto = (ImageView)context.findViewById(id.listingPhoto_img);
	    companyLogo = (ImageView)context.findViewById(id.companyLogo);
	    detailImage = (ImageView)context.findViewById(R.id.detailImage);
       
	    
		if(isCommercial){
			parkingType = (TextView)context.findViewById(id.parkingType);
			sqft = (TextView)context.findViewById(id.sqft);
		}
		else{
			bed = (TextView)context.findViewById(id.bed);
			bath = (TextView)context.findViewById(id.bath);
		}	
		
		photosViewPager = (ViewPager)context.findViewById(id.photopager);
		
	}
}
