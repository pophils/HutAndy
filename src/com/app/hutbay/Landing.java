package com.app.hutbay;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.app.hutbay.R.id;
import com.app.hutbay.adapter.DrawerAdapter;
import com.app.hutbay.fragment.FilterSearch;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
 

public class Landing extends SherlockFragmentActivity {
	
	 private DrawerLayout drawer;
	 private ListView drawerListView;
	 private ActionBarDrawerToggle drawerToggle;	 
	 Fragment filterSearchFragment;
	 private boolean isFirstBackmenuKeyClick;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 ActionBar actionBar = getSupportActionBar();
		 actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_default_background));
		 setContentView(R.layout.landing);		
		
	
		 
		if(findViewById(id.landingFragmentContainer) != null){
			
			// in case activity is restored from a previous state
			// return prevent overlapping fragments
			if(savedInstanceState != null){
				initDrawerLayout();
				return;
			}
			
			filterSearchFragment = new FilterSearch();
			getSupportFragmentManager().beginTransaction()
			.add(id.landingFragmentContainer, filterSearchFragment, "filterSearchFrag")
			.commit();			
		}	
		
		initDrawerLayout();	

	}

	 @Override
	   public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        isFirstBackmenuKeyClick = false;
	         
	    }
	
	 @Override
    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        if (item.getItemId() == android.R.id.home) {

	            if (drawer.isDrawerOpen(drawerListView)) {
	                drawer.closeDrawer(drawerListView);
	            } else {
	                drawer.openDrawer(drawerListView);
	            }
	        }

	        return super.onOptionsItemSelected(item);
	    }


	    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

	        if(keyCode == KeyEvent.KEYCODE_MENU){

	            if (drawer.isDrawerOpen(drawerListView)) {
	                drawer.closeDrawer(drawerListView);
	            } else {
	                drawer.openDrawer(drawerListView);
	            }
	            return true;
	        }
	       
	        if(keyCode == KeyEvent.KEYCODE_BACK){
	        	if(!isFirstBackmenuKeyClick){
	        		Toast.makeText(getBaseContext(), "Press back again to exit Hutbay.", Toast.LENGTH_SHORT) 
		    		.show();
	        		isFirstBackmenuKeyClick = true;
	        	}	        	
	        	else
	        		finish();
	        	
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	
 @Override
    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        drawerToggle.syncState();
	    } 
 
    private void initDrawerLayout(){

	        final String[] drawerItems = getResources().getStringArray(R.array.drawer_list_item_values);
	        drawer = (DrawerLayout) findViewById(R.id.landing_drawer_layout);
	        drawerListView = (ListView) findViewById(R.id.landing_drawer);

	        DrawerAdapter adapter = new DrawerAdapter(false, getSupportFragmentManager(), Landing.this, R.layout.drawer_list_row, drawerItems, drawerListView, drawer);
	        drawerListView.setAdapter(adapter);
 
	        adapter.setCustomItemClickListener();

	        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_drawer,R.string.open_drawer, R.string.close_drawer) {

	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(getSupportActionBar().getTitle());
	            }

	            public void onDrawerOpened(View drawerView) {
	               // getActionBar().setTitle("Open Drawer");
	            }
	        };

	        drawer.setDrawerListener(drawerToggle);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        getSupportActionBar().setHomeButtonEnabled(true);
	    }

    public void closeDrawer(){
	  drawer.closeDrawers();
  }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	isFirstBackmenuKeyClick = false;
    }
}
