package com.exale.navisdynamicuiforswap;

import java.util.HashSet;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class DashboardUi extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
     setContentView(R.layout.dashboardsettingsui); 
     TextView allitemstobedisplayed=(TextView) findViewById(R.id.allitemstobedisplayed);
     
     HashSet<String> scannedAppSet = (HashSet<String>) Prefrences.getScannedAppSet( getApplicationContext());
     if (scannedAppSet != null) {
         Iterator<String> scannedAppIterator = scannedAppSet.iterator();
         while (scannedAppIterator.hasNext()) {

             String nextItems = scannedAppIterator.next();
             allitemstobedisplayed.append("\n"+ nextItems);
             
             Button myButton = new Button(this);
             myButton.setText(nextItems);
             myButton.  setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_launcher),null,null);
             LinearLayout ll = (LinearLayout)findViewById(R.id.addbuttonlayout);
             LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
             ll.addView(myButton, lp);
             
             
             
             Log.i("nextItems", nextItems);
}
     
        
    }

    }}

