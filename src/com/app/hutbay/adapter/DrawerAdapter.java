package com.app.hutbay.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hutbay.Landing;
import com.app.hutbay.R;
import com.app.hutbay.activity.About;
import com.app.hutbay.activity.Alert;
import com.app.hutbay.activity.FeedBack;
import com.app.hutbay.fragment.SearchAll;


public class DrawerAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener{

    private Context context;
    private String[] drawerListItems;
    private ListView drawerListView;
    private DrawerLayout drawer;
    private FragmentManager supportFragmentManager;
    private boolean fromSearchResult;

    public DrawerAdapter(boolean fromSearchResult, FragmentManager supportFragmentManager, Context context, int rowViewResourceId, String[] drawerListItems, ListView drawerListView, DrawerLayout drawer){
        super(context, rowViewResourceId, drawerListItems);
        this.context = context;
        this.drawerListItems = drawerListItems;
        this.drawerListView = drawerListView;
        this.drawer = drawer;
        this.supportFragmentManager = supportFragmentManager;
        this.fromSearchResult = fromSearchResult;
     }
  
	@Override
    public View getView(int itemPosition, View convertView, ViewGroup viewGroup) {

        View row = convertView;
        DrawerListRowTemplate template;

        if(row == null){
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             row = inflater.inflate(R.layout.drawer_list_row, null);
             template = new DrawerListRowTemplate(row);
             row.setTag(template);
        }
        else{
            template = (DrawerListRowTemplate)row.getTag();
        }

        template.LoadValuesFrom(drawerListItems[itemPosition]);

        return row;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    	
    	 
    	//((Landing)context).closeDrawer();
    	drawer.closeDrawers();
    	
    	SearchAll searchAllFrag = new SearchAll();
    	
    	if(!fromSearchResult){
    		  switch(position + 1){
          	case 1:
          		SearchAll.searchUrl = context.getResources().getString(R.string.getAllListingUrl)
          		                      + "?type=1&pageNo=1";
          		SearchAll.isForSaleSearch = true;
          		
          		supportFragmentManager
           		  .beginTransaction()
           		  .add(searchAllFrag, "searchAllFrag")
           		  .commit();  
          		break;
          	case 2:
          	   SearchAll.searchUrl = context.getResources().getString(R.string.getAllListingUrl)
          					      + "?type=2&pageNo=1";
          	   SearchAll.isForSaleSearch = false;
          	   
          	   supportFragmentManager
         		  .beginTransaction()
         		  .add(searchAllFrag, "searchAllFrag")
         		  .commit();  
      		   break;   
      		   
          	case 3:
          		 Intent loadAlertActivity = new Intent(context, Alert.class);
          		    		  
          		 context.startActivity(loadAlertActivity); 
       		   break; 
       		   
          	case 4:
         		 Intent loadFeedbackActivity = new Intent(context, FeedBack.class);
         		    		  
         		 context.startActivity(loadFeedbackActivity); 
      		   break; 
      		   
      		   
          	case 5:
         		 Intent loadAboutActivity = new Intent(context, About.class);
         		    		  
         		 context.startActivity(loadAboutActivity); 
      		   break; 
          }
    	}
    	else{
    		  switch(position + 1){
    		  
          	case 1:
          		 Intent loadAlertActivity = new Intent(context, Alert.class);
          		    		  
          		 context.startActivity(loadAlertActivity); 
       		   break; 
       		   
          	case 2:
         		 Intent loadFeedbackActivity = new Intent(context, FeedBack.class);
         		    		  
         		 context.startActivity(loadFeedbackActivity); 
      		   break; 
      		   
      		   
          	case 3:
         		 Intent loadAboutActivity = new Intent(context, About.class);
         		    		  
         		 context.startActivity(loadAboutActivity); 
      		   break; 
          }
    	}            
        
         }

    public void setCustomItemClickListener() {
        drawerListView.setOnItemClickListener(this);
    }

    // this serve as a template for each listView row
    class DrawerListRowTemplate{

        private View row;
        private TextView itemText;
        private ImageView itemIcon;

        DrawerListRowTemplate(View row){
             this.row =row;
             this.itemText = (TextView)this.row.findViewById(R.id.drawer_menu_text);
             this.itemIcon = (ImageView)this.row.findViewById(R.id.drawer_menu_icon);

         }

          void LoadValuesFrom(String drawerItemText){
            itemText.setText(drawerItemText);

            if(!fromSearchResult){
            	if (drawerItemText.equals(drawerListItems[0])) {
                    itemIcon.setImageResource(R.drawable.forsale_icon);
                }

                else if (drawerItemText.equals(drawerListItems[1])) {
                    itemIcon.setImageResource(R.drawable.torent_icon);
                }

                else if (drawerItemText.equals(drawerListItems[2])) {
                    itemIcon.setImageResource(R.drawable.alert_icon);
                }

                else if (drawerItemText.equals(drawerListItems[3])) {
                    itemIcon.setImageResource(R.drawable.request);
                }

                else if (drawerItemText.equals(drawerListItems[4])) {
                    itemIcon.setImageResource(R.drawable.about_icon);
                }      
            }
            
            else{      
                if (drawerItemText.equals(drawerListItems[0])) {
                    itemIcon.setImageResource(R.drawable.alert_icon);
                }

                else if (drawerItemText.equals(drawerListItems[1])) {
                    itemIcon.setImageResource(R.drawable.request);
                }

                else if (drawerItemText.equals(drawerListItems[2])) {
                    itemIcon.setImageResource(R.drawable.about_icon);
                }      
            }
                    

          }
    }


}
