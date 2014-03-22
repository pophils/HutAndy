package com.app.hutbay.dialog;

import com.app.hutbay.R;

import android.app.Activity;
import android.app.Dialog;

public class LoadingGif extends Dialog  {

	public LoadingGif(Activity host) {
		
		  super(host, R.style.FullScreenDialog);
		  setContentView(R.layout.dialog_loading_gif);	 
		  setCanceledOnTouchOutside(false);		
		 
	}	 
}
