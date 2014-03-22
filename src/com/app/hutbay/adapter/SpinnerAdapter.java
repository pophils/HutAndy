package com.app.hutbay.adapter;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public  class SpinnerAdapter implements AdapterView.OnItemSelectedListener {

   private Spinner spinner;
   private ArrayAdapter<CharSequence> adapter;
   private int spinnerItemsResourceId;
   private int spinnerItemResourceId;
   private Activity activity;
   private boolean firstSpinnerLoad;  // prevent onchange to be executed at first load

    public SpinnerAdapter(Activity activity, Spinner spinner, int spinnerItemsResourceId, int spinnerItemResourceId){
        this.activity = activity;
        this.spinner = spinner;
        this.spinnerItemsResourceId = spinnerItemsResourceId;
        this.spinnerItemResourceId = spinnerItemResourceId;
        this.firstSpinnerLoad = true;
    }

    public void LoadContent() {
        adapter = ArrayAdapter.createFromResource(activity, spinnerItemsResourceId, spinnerItemResourceId);        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    
    public void LoadContent(ArrayList<CharSequence> list) {
   
        adapter = new ArrayAdapter<CharSequence>(activity.getBaseContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    
    public void RemoveContent() {
       adapter.clear();
       adapter.notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(){
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {

        if(!firstSpinnerLoad){
            //execute logic e.g  Object itemAtPosition = parent.getSelectedItem();
            //Id and Position are the same
        }
        firstSpinnerLoad = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

	public void notifyDataSetChanged() {
	  adapter.notifyDataSetChanged();
	}

}
