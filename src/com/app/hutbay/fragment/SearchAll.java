package com.app.hutbay.fragment;

import com.app.hutbay.R;
import com.app.hutbay.activity.AllSearchResult;
import com.app.hutbay.utility.Konstant;

import Task.SearchAllAjaxTask;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public class SearchAll extends Fragment{

	private Activity host;
	private SearchAllAjaxTask ajaxTask;
	public static Dialog loadingGifDialog;
	public static String searchUrl;
	public static boolean isForSaleSearch;
	
	@Override
	public void onAttach(Activity activity) {
		 super.onAttach(activity);
		 this.host = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setRetainInstance(true);	
	
		loadingGifDialog = new Dialog(host, R.style.FullScreenDialog);
		loadingGifDialog.setContentView(R.layout.dialog_loading_gif);
	    
		loadingGifDialog.setCancelable(true);
		loadingGifDialog.setCanceledOnTouchOutside(false);
		setDialogOnkeyListener();
		 
		ajaxTask = new SearchAllAjaxTask(loadingGifDialog, host, this);
		ajaxTask.execute(searchUrl);
	}
	

	private void setDialogOnkeyListener(){
		
		loadingGifDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_BACK){
                    
                	try{
                		ajaxTask.cancel(true);
                	}
                	catch(Exception ex){
                		return false;
                	}	                	
                }
                return false;
            }
	});
	
	}
	
	public void disposeTask(){
		ajaxTask = null;
	}
	
	public void processResult(String jsonresult){		
		ajaxTask.cancel(true);		
		Intent loadSearchResultActivity = new Intent(host, AllSearchResult.class);
		loadSearchResultActivity.putExtra(Konstant.TAG_IS_FORSALE_SEARCH_XTRA, isForSaleSearch);
		loadSearchResultActivity.putExtra(Konstant.TAG_JSON_RESULT_XTRA, jsonresult);		 
		startActivity(loadSearchResultActivity);		
	}
}





