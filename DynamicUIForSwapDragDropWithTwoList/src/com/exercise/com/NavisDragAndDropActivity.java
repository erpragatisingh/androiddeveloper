package com.exercise.com;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * @author Pragati Singh
 *
 */
public class NavisDragAndDropActivity extends Activity {

	LinearLayout targetLayout = null;
	LinearLayout sourcelayout = null;
	ListView listSource = null;
	ListView listTarget = null;
	HashSet<String> seetingsDashboardArray;
	int tempPossion = 0;
	String commentMsg = null;
	Button dashboard = null;
	MyDragEventListener myDragEventListener = new MyDragEventListener();
	List<String> sourceList = null;
	List<String> droppedList = null;
	ArrayAdapter<String> droppedAdapter = null;
	ArrayAdapter<String> sourceAdapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		targetLayout = (LinearLayout) findViewById(R.id.targetlayout);
		sourcelayout = (LinearLayout) findViewById(R.id.sourcelayout);
		listSource = (ListView) findViewById(R.id.sourcelist);
		listTarget = (ListView) findViewById(R.id.targetlist);
		dashboard = (Button) findViewById(R.id.dashboard);
		dashboard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(getApplicationContext(),
						DashboardUi.class));

			}
		});

		// Create and set the tags for the Buttons
		final String SOURCELIST_TAG = "listSource";
		final String TARGETLIST_TAG = "listTarget";
		final String TARGETLAYOUT_TAG = "targetLayout";
		// add values to source array
		sourceList = new ArrayList<String>();
		sourceList.add("element_1");
		sourceList.add("element_2");
		sourceList.add("element_3");

		// set tag to UI to make it unique

		listSource.setTag(SOURCELIST_TAG);
		listTarget.setTag(TARGETLIST_TAG);
		targetLayout.setTag(TARGETLAYOUT_TAG);

		// set adapter to for listSource

		sourceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, sourceList);
		listSource.setAdapter(sourceAdapter);
		listSource.setOnItemLongClickListener(listSourceItemLongClickListener);

		// Add values to droppedList
		droppedList = new ArrayList<String>();
		droppedList.add("element_6");
		droppedList.add("element_7");
		droppedList.add("element_8");

		// set adapter to for droppedAdapter/target

		droppedAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, droppedList);
		listTarget.setAdapter(droppedAdapter);

		listSource.setOnDragListener(myDragEventListener);
		targetLayout.setOnDragListener(myDragEventListener);

		// add values for show UI on dashboard

		seetingsDashboardArray = new HashSet<String>();
		seetingsDashboardArray.addAll(droppedList);
		Prefrences.setScannedAppSet(getApplicationContext(),
				seetingsDashboardArray);
	}

	OnItemLongClickListener listSourceItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> l, View v, int position,
				long id) {
			tempPossion = position;
			// Selected item is passed as item in dragData
			ClipData.Item item = new ClipData.Item(sourceList.get(position));

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData((CharSequence) v.getTag(),
					clipDescription, item);
			DragShadowBuilder myShadow = new MyDragShadowBuilder(v);

			v.startDrag(dragData, // ClipData
					myShadow, // View.DragShadowBuilder
					sourceList.get(position), // Object myLocalState
					0); // flags

			commentMsg = v.getTag() + " : onLongClick.\n";
			Log.i("commentMsg >", commentMsg);

			return true;
		}
	};

	private static class MyDragShadowBuilder extends View.DragShadowBuilder {
		private static Drawable shadow;

		public MyDragShadowBuilder(View v) {
			super(v);
			shadow = new ColorDrawable(Color.LTGRAY);
		}

		@Override
		public void onProvideShadowMetrics(Point size, Point touch) {
			int width = getView().getWidth();
			int height = getView().getHeight();

			shadow.setBounds(0, 0, width, height);
			size.set(width, height);
			touch.set(width / 2, height / 2);
		}

		@Override
		public void onDrawShadow(Canvas canvas) {
			shadow.draw(canvas);
		}

	}

	protected class MyDragEventListener implements View.OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			final int action = event.getAction();

			switch (action) {
			case DragEvent.ACTION_DRAG_STARTED:
				// All involved view accept ACTION_DRAG_STARTED for
				// MIMETYPE_TEXT_PLAIN
				if (event.getClipDescription().hasMimeType(
						ClipDescription.MIMETYPE_TEXT_PLAIN)) {
					commentMsg += v.getTag()
							+ " : ACTION_DRAG_STARTED accepted.\n";
					Log.i("commentMsg >", commentMsg);
					return true; // Accept
				} else {
					commentMsg += v.getTag()
							+ " : ACTION_DRAG_STARTED rejected.\n";
					Log.i("commentMsg >", commentMsg);
					return false; // reject
				}
			case DragEvent.ACTION_DRAG_ENTERED:
				commentMsg += v.getTag() + " : ACTION_DRAG_ENTERED.\n";
				Log.i("commentMsg >", commentMsg);
				return true;
			case DragEvent.ACTION_DRAG_LOCATION:
				commentMsg += v.getTag() + " : ACTION_DRAG_LOCATION - "
						+ event.getX() + " : " + event.getY() + "\n";
				Log.i("commentMsg >", commentMsg);
				return true;
			case DragEvent.ACTION_DRAG_EXITED:
				commentMsg += v.getTag() + " : ACTION_DRAG_EXITED.\n";
				Log.i("commentMsg >", commentMsg);
				return true;
			case DragEvent.ACTION_DROP:
				// Gets the item containing the dragged data
				ClipData.Item item = event.getClipData().getItemAt(0);

				commentMsg += v.getTag() + " : ACTION_DROP" + "\n";
				Log.i("commentMsg >", commentMsg);

				// If apply only if drop on buttonTarget
				if (v == targetLayout) {
					String droppedItem = item.getText().toString();

					commentMsg += "Dropped item - " + droppedItem + "\n";
					Log.i("commentMsg >", commentMsg);
					droppedList.remove(tempPossion);
					droppedList.add(droppedItem);
					seetingsDashboardArray.removeAll(droppedList);
					String tempVAl = droppedList.get(tempPossion);
					sourceList.remove(tempPossion);
					sourceList.add(tempVAl);
					seetingsDashboardArray.clear();
					seetingsDashboardArray.addAll(droppedList);

					// save updated values to show ui on dashboard

					Prefrences.setScannedAppSet(getApplicationContext(),
							seetingsDashboardArray);

					// refraesh list items
					droppedAdapter.notifyDataSetChanged();
					sourceAdapter.notifyDataSetChanged();
					return true;
				} else {
					return false;
				}

			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {
					commentMsg += v.getTag()
							+ " : ACTION_DRAG_ENDED - success.\n";
					Log.i("commentMsg >", commentMsg);
				} else {
					commentMsg += v.getTag() + " : ACTION_DRAG_ENDED - fail.\n";
					Log.i("commentMsg >", commentMsg);
				}
				;
				return true;
			default: // unknown case
				commentMsg += v.getTag() + " : UNKNOWN !!!\n";
				Log.i("commentMsg >", commentMsg);
				return false;

			}
		}
	}
}