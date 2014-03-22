package com.app.hutbay.adapter;

import java.util.List;

import com.app.hutbay.R;
import com.app.hutbay.activity.ListingDetails;
import com.app.hutbay.fragment.DetailPhoto;
import com.app.hutbay.utility.Konstant;
import com.squareup.picasso.Picasso;

import Task.LoadDetailsPhotoTask;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DetailsPhotoPagerAdapter  extends PagerAdapter  {
	
    private final List<String> photoPaths;
    private Context host;
    private boolean nophoto;
    
    public DetailsPhotoPagerAdapter(List<String> photoPaths, Context host) {
              this.photoPaths = photoPaths;
              this.host = host;
    }
   
    
    public DetailsPhotoPagerAdapter(List<String> photoPaths, Context host, boolean nohpoto) {
        this.photoPaths = photoPaths;
        this.host = host;
        this.nophoto = nohpoto;
}
    @Override
    public int getCount() {
        return photoPaths.size();
    }


    @Override
    public Object instantiateItem(View container, int position) {
    	
    	 LayoutInflater inflater = (LayoutInflater) container.getContext()
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	 
          FrameLayout imageLayout = (FrameLayout) inflater.inflate(R.layout.detail_image_loader, null);
          ImageView imageView = (ImageView) imageLayout.findViewById(R.id.detailImage);
          ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.detailImageSpinner);

          spinner.setVisibility(View.VISIBLE);
         
          ViewPager parent = (ViewPager) container;              
		  new LoadDetailsPhotoTask(parent, host, spinner, imageView).execute(photoPaths.get(position));		 

		   parent.addView(imageLayout,0);
    	   return imageLayout;
    }

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == ((FrameLayout) obj);
	}
	
	 @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	      ((ViewPager) container).removeView((FrameLayout) object);
	    }
}