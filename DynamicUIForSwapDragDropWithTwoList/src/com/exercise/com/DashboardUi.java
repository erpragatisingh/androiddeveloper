package com.exercise.com;

import java.util.HashSet;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Pragati Singh
 *
 */
public class DashboardUi extends Activity implements OnClickListener {
	Button myButton = null;
	TextView tvOption1 = null;
	TextView tvOption2 = null;
	TextView tvOption3 = null;
	LinearLayout trackYourHealth= null;
	LinearLayout  Services= null;
	LinearLayout layoutFirstsaidlibrary= null;
	ImageView optionImage1=null;
	ImageView optionImage2=null;
	ImageView optionImage3=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dashboardsettingsui);
		tvOption1 = (TextView) findViewById(R.id.option1);
		tvOption2 = (TextView) findViewById(R.id.option2);
		tvOption3 = (TextView) findViewById(R.id.option3);
		optionImage1 = (ImageView) findViewById(R.id.image_option1);
		optionImage2 = (ImageView) findViewById(R.id.image_option2);
		optionImage3 = (ImageView) findViewById(R.id.image_option3);
		trackYourHealth = (LinearLayout) findViewById(R.id.layout_healthtrack);
		Services = (LinearLayout) findViewById(R.id.layout_services);
		layoutFirstsaidlibrary = (LinearLayout) findViewById(R.id.layout_firstsaidlibrary);
		 
		// reading saved item valued from preferences
		HashSet<String> scannedAppSet = (HashSet<String>) Prefrences
				.getScannedAppSet(getApplicationContext());

		if (scannedAppSet != null) {
			Iterator<String> scannedAppIterator = scannedAppSet.iterator();
			while (scannedAppIterator.hasNext()) {
				String nextItems = scannedAppIterator.next();
				if (nextItems.contains("element_2")) {
					// set id to identify view uniquely
					trackYourHealth.setId(1);
					tvOption1.setText("element_2");
					 
					tvOption1.setId(1);
					optionImage1.setBackground(getResources().getDrawable(
							R.drawable.health));
				}
				if (nextItems.contains("element_8")) {
					// set id to identify view uniquely
					layoutFirstsaidlibrary.setId(2);
					tvOption2.setText("element_8");
					tvOption2.setId(2);
					optionImage2.setBackground(getResources().getDrawable(
							R.drawable.first_aid));

				}
				if (nextItems.contains("element_6")) {
					// set id to identify view uniquely
					Services.setId(3);
					tvOption3.setText("element_6");
					tvOption3.setId(3);
					optionImage3.setBackground(getResources().getDrawable(
							R.drawable.services));
				}

				 
			}
			// make layout Clickable 
			trackYourHealth.setOnClickListener(this);
			layoutFirstsaidlibrary.setOnClickListener(this);
			Services.setOnClickListener(this);

		}

	}
	// Performing click event
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case 1:
			Toast.makeText(getApplicationContext(),
					"You have selected" + tvOption1.getText(), Toast.LENGTH_LONG)
					.show();
			 
			break;
		 
		case 2:
			Toast.makeText(getApplicationContext(),
					"You have selected" + tvOption2.getText(), Toast.LENGTH_LONG)
					.show();
			break;
		case 3:
			Toast.makeText(getApplicationContext(),
					"You have selected" + tvOption3.getText(), Toast.LENGTH_LONG)
					.show();
			break;
		
		default:
			break;
		}

	}
}
