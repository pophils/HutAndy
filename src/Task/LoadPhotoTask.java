package Task;

import com.app.hutbay.utility.Konstant;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadPhotoTask extends AsyncTask<String, Void, String> {
     
    
    int orientation;
    ImageView imageContainer;
    Context context;
         
    
    public LoadPhotoTask(ImageView imageContainer, Context context){
    	this.imageContainer = imageContainer;
    	this.context = context;    	 
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
    	
		  Picasso.with(context) 
   		  .load(Konstant.TAG_DETAILS_PHOTO_URL + photoPath)      		  
   	     .resize(250, 190)
   		  .into(imageContainer); 	    
    }

   
}

