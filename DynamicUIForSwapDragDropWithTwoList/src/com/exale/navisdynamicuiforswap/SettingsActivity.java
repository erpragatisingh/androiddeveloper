package com.exale.navisdynamicuiforswap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SettingsActivity extends ActionBarActivity {

    // String[] arrayList1 = new
    // String[]{"element_1","element_2","element_3","element_4","element_5"};
    ArrayList<String> arrayList1 = new ArrayList<String>();
    HashSet<String> seetingsDashboardArray;
    ArrayList<String> arrayList2 = new ArrayList<String>();
    ListView list1, list2;
    int swap_id;
    LinearLayout targetLayout;
    ArrayAdapter<String> adapter_list2;

    MyDragEventListener myDragEventListener = new MyDragEventListener();
    ArrayAdapter<String> list2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_main);
        list1 = (ListView) findViewById(R.id.sourcelist);
        list2 = (ListView) findViewById(R.id.targetlist);
        targetLayout = (LinearLayout) findViewById(R.id.targetlayout);

        // add values to array 1
        arrayList1.add("element_1");
        arrayList1.add("element_2");
        arrayList1.add("element_3");
     
        ArrayAdapter<String> adapter_list1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, arrayList1);
        list1.setAdapter(adapter_list1);
        list1.setOnItemLongClickListener(list1ItemLongClickListener);
        // add values to array 2

        arrayList2.add("SecondList_element_6");
        arrayList2.add("SecondList_element_7");
        arrayList2.add("SecondList_element_8");
        
        adapter_list2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
                arrayList2);
        list2.setAdapter(adapter_list2);

        list1.setOnDragListener(myDragEventListener);
        list2.setOnDragListener(myDragEventListener);

         seetingsDashboardArray = new HashSet<String>();
        seetingsDashboardArray.addAll(arrayList1);
       // Prefrences.setScannedAppSet(getApplicationContext(), seetingsDashboardArray);

    }

    OnItemLongClickListener list1ItemLongClickListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {
            ClipData.Item item = new ClipData.Item((CharSequence) arrayList1.get(position));
            swap_id = position;
            String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData dragData = new ClipData((CharSequence) v.getTag(), clipDescription, item);
            DragShadowBuilder myShadow = new MyDragShadowBuilder(v);
            adapter_list2.add( arrayList1.get(position+1));
            v.startDrag(dragData, // ClipData
                    myShadow, // View.DragShadowBuilder
                    arrayList1.get(position), // Object myLocalState
                    0); // flags
            seetingsDashboardArray.add( arrayList2.get(position));
            seetingsDashboardArray.remove(arrayList1.get(position));
            Prefrences.setScannedAppSet(getApplicationContext(), seetingsDashboardArray);
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
        public boolean onDrag(View view, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);
                // If apply only if drop on buttonTarget
                if (view == targetLayout) {
                    String droppedItem = item.getText().toString();
                    ViewGroup viewgroup = (ViewGroup) view.getParent();
                    viewgroup.removeView(view);
                    Log.i("droppedItem = ", droppedItem);
                    //Collections.swap(arrayList2, view.getId(), view.getId()); // it for swap   two values of single array
                    adapter_list2.notifyDataSetChanged();
                    LinearLayout containView = (LinearLayout) view;
                    containView.addView(view);
                    view.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    String droppedItem = item.getText().toString();
                    adapter_list2.add(droppedItem);
                    adapter_list2.notifyDataSetChanged();
                    return false;
                }

            default: // unknown case

                return false;
            }

        }

    }
}

/**
 * A placeholder fragment containing a simple view.
 */

