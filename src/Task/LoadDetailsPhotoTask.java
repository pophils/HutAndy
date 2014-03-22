package Task;

import com.app.hutbay.R;
import com.app.hutbay.utility.Konstant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class LoadDetailsPhotoTask extends AsyncTask<String, Void, String> {
     
    
    int orientation;
    ImageView imageContainer;
    Context context;
    ProgressBar spinner;
    ViewPager parent;    
    ImageView imageView;
    public LoadDetailsPhotoTask(ViewPager parent, Context context, ProgressBar  spinner, ImageView imageView){
    	this.parent = parent;
    	this.context = context;
    	this.spinner = spinner;
    	this.imageView = imageView;
    	
    }
    
    @Override
    protected void onPreExecute(){
      
    }

    @Override
    protected String doInBackground(String... urls){
       
        return urls[0];
    }
    
    @Override
    protected void onPostExecute(String photoPath) {    	
    		  
    		  if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT )
    			  imageView.setScaleType(ScaleType.CENTER_CROP);    			  
    	     else if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
    	    	  imageView.setScaleType(ScaleType.CENTER_INSIDE); 
    		   
    		      imageView.setAdjustViewBounds(true); 	 
    		  
    		  
    		  Picasso.with(context) 
	   		  .load(Konstant.TAG_DETAILS_PHOTO_URL + photoPath) 	   		   
	   		  .into(imageView, new Callback() {
    		      @Override 
    		      public void onSuccess() {
    		      
    		    	  imageView.setVisibility(View.VISIBLE);
    		    	  spinner.setVisibility(View.GONE);
    		      }

				@Override
				public void onError() {				 
					  
					  Picasso.with(context) 
			   		  .load(R.drawable.nodetailphoto) 			   		   
			   		 .into(imageView, new Callback() {
		    		      @Override 
		    		      public void onSuccess() {		    		      
		    		    	  imageView.setVisibility(View.VISIBLE);
		    		    	  spinner.setVisibility(View.GONE);		    		    	 
		 					  imageView.setScaleType(ScaleType.CENTER);  
		 					  imageView.setAdjustViewBounds(true); 	
		    		      }

						@Override
						public void onError() {
							 spinner.setVisibility(View.GONE); 							 
						}
						
		    		    }); 
				}
				
    		    });      		 
    }

   
}

