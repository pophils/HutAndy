package com.app.hutbay.dialog;

import com.app.hutbay.R;
import com.app.hutbay.R.id;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class CustomFormDialog  extends DialogFragment{ 
 

	  @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
		  
		  
		  return super.onCreateDialog(savedInstanceState);
		  /*
	        // Build the dialog and set up the button click handlers
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        
	        
	        LayoutInflater inflater = getActivity().getLayoutInflater();

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        builder.setView(inflater.inflate(R.layout.test_layout, null))     
	        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the positive button event back to the host activity
	                      // mListener.onDialogPositiveClick(CustomFormDialog.this);
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the negative button event back to the host activity
	                     //  mListener.onDialogNegativeClick(CustomFormDialog.this);
	                   }
	               });
	        
	        
	        return builder.create();*/
	    }
	  
	  
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	      //  setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
	    }
	  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  
		  getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
	     // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	      getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.form_dialog_bg));
		 
	      LayoutParams params = getDialog().getWindow().getAttributes();
	        params.width = LayoutParams.WRAP_CONTENT;
	        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
	  // super.onCreateView(inflater, container, savedInstanceState);
	      return inflater.inflate(R.layout.contact_agent, container, false);
	//	return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}
