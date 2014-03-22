package com.app.hutbay.utility;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable; 
import android.net.Uri;

import com.actionbarsherlock.app.ActionBar;
import com.app.hutbay.Landing;
import com.app.hutbay.R;
import com.app.hutbay.activity.ErrorView;
 
public class ViewHelper extends Activity {

    public static void ChangeActionBarBackground(Context context,ActionBar actionBar, int drawable){
        Resources rs = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(rs, drawable);
        BitmapDrawable actionBarBackground = new BitmapDrawable(rs, bitmap);
        actionBar.setBackgroundDrawable(actionBarBackground);
    }    
     
    public static void ChangeActionBarBackgroundColor(Context context,ActionBar actionBar){
    	 actionBar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.custom_default_background));
    }
    
    public static Map<String, String> initListingTypeDict()
    {
    	
    	Map<String,String> propertyTypeDictionary = new HashMap<String,String>();     	
    	
    	propertyTypeDictionary.put("All", "0");
    	propertyTypeDictionary.put("Bungalow", "5");
    	propertyTypeDictionary.put("Detached", "4");
    	propertyTypeDictionary.put("Duplex", "1");
    	propertyTypeDictionary.put("Flat", "2");
    	propertyTypeDictionary.put("Land", "14");  
    	
    	propertyTypeDictionary.put("Mansion", "8");
    	propertyTypeDictionary.put("Office Space", "10");
    	propertyTypeDictionary.put("Semi-Detached", "7");
    	propertyTypeDictionary.put("Shop", "11");
    	propertyTypeDictionary.put("Studio", "6");
    	propertyTypeDictionary.put("Terrace", "3");
    	
    	propertyTypeDictionary.put("TownHouse", "9");
    	propertyTypeDictionary.put("PentHouse", "13");
    	propertyTypeDictionary.put("Warehouse", "12");

        return propertyTypeDictionary;
    }
    
    public static Map<String, String> initAlertListingTypeDict()
    {
    	
    	Map<String,String> propertyTypeDictionary = new HashMap<String,String>();     	
    	
    	propertyTypeDictionary.put("Select your property preference...", "-1");
    	propertyTypeDictionary.put("All Property Types", "0");
    	propertyTypeDictionary.put("Bungalow", "5");
    	propertyTypeDictionary.put("Detached", "4");
    	propertyTypeDictionary.put("Duplex", "1");
    	propertyTypeDictionary.put("Flat", "2");
    	propertyTypeDictionary.put("Land", "14");  
    	
    	propertyTypeDictionary.put("Mansion", "8");
    	propertyTypeDictionary.put("Office Space", "10");
    	propertyTypeDictionary.put("Semi-Detached", "7");
    	propertyTypeDictionary.put("Shop", "11");
    	propertyTypeDictionary.put("Studio", "6");
    	propertyTypeDictionary.put("Terrace", "3");
    	
    	propertyTypeDictionary.put("TownHouse", "9");
    	propertyTypeDictionary.put("PentHouse", "13");
    	propertyTypeDictionary.put("Warehouse", "12");

        return propertyTypeDictionary;
    }
  
    public static void ChangeActionBarTitle(Context context,ActionBar actionBar, String title){
        actionBar.setTitle(title);
    }   
     
    public void initConnectionCheck(Context context){
    	
    	if(!NetworkManager.isConnectionAvailable(context)){
    		
    		 Intent loadErrorActivity = new Intent(context, ErrorView.class);

    		 loadErrorActivity.putExtra(ErrorView.ErrorTitleExtra, "Connection Error");
    		 loadErrorActivity.putExtra(ErrorView.errorMessageExtra, "Your device appears not to be connected to the internet. Please check your connection");
    	 	    		  
    		 startActivity(loadErrorActivity);    		
    	}
    }
    
    public void redirectToErrorView(Context context, String title, String message){
    	
    		 Intent loadErrorActivity = new Intent(context, ErrorView.class);

    		 loadErrorActivity.putExtra(ErrorView.ErrorTitleExtra, title);
    		 loadErrorActivity.putExtra(ErrorView.errorMessageExtra, message);
    	 	    		  
    		 startActivity(loadErrorActivity);       
    }    

    public static void showGenericDialog(Context context, String title, String message, String okButtonText){
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	builder.setTitle(title)
    	.setMessage(message)
    	.setPositiveButton(okButtonText, null)
    	.create().show();    
    }    
 
    public static boolean validateEmail(String email){
    	
    	String emailReg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    	
    	return email.matches(emailReg);
    }
    
    public static boolean validatePhone(String phoneNo){
    	
    	String phoneNoReg = "^[0-9+][0-9]{1,}$";
    	
    	return phoneNo.matches(phoneNoReg);
    }

    public static void mailTo(Context context, String email, String subject, String message, String mailClientChooserHeader) {

	   Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
		 String uriText = "mailto:" + Uri.encode(email) + 
		           "?subject=" + Uri.encode(subject) + 
		           "&body=" + Uri.encode(message);
		 Uri uri = Uri.parse(uriText);

		 mailIntent.setData(uri);
		 mailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(mailIntent, mailClientChooserHeader));
}
   
    public static void callOut(Context context, String phoneNo) {

		 Intent callIntent = new Intent(Intent.ACTION_CALL);          
         callIntent.setData(Uri.parse("tel: "+ phoneNo));  
         callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(callIntent);
   }
   
    public static void sendSms(Context context, String address,  String message) {

	     Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		 smsIntent.setType("vnd.android-dir/mms-sms");
		 smsIntent.putExtra("address", address);
		 smsIntent.putExtra("sms_body", message);
		 smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 context.startActivity(smsIntent);
  }

    public static void share(Context context, String shareTitle,  String link, String subject) {

	   
	     Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		 intent.setType("text/plain");
		 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);	
		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		 intent.putExtra(Intent.EXTRA_TEXT, link);
		 
		 context.startActivity(Intent.createChooser(intent, shareTitle));
   }
   
    public static void browse(Context context, String url) {
 
	   Intent intent = new Intent(Intent.ACTION_VIEW);
	   intent.setData(Uri.parse(url));
	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   context.startActivity(intent);
   }  
    
    public static void letsGoHome(Context context) {
	  
	   Intent loadLandingActivity = new Intent(context, Landing.class);
	   loadLandingActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	   context.startActivity(loadLandingActivity);  	 
   }     
}


