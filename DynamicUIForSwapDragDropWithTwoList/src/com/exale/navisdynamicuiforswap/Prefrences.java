package com.exale.navisdynamicuiforswap;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Prefrences {
   public static final String MG_SCAN_APP_LIST="MG_SCAN_APP_LIST"; 
  /*//Reading values from set 
    HashSet<String> scannedAppSet = (HashSet<String>) Preferences.getScannedAppSet(context);
                if (scannedAppSet != null) {
                    Iterator<String> scannedAppIterator = scannedAppSet.iterator();
                    while (scannedAppIterator.hasNext()) {

                        String nextPackage = scannedAppIterator.next();
    }

    // Save valus to set
       Preferences.setScannedAppSet(getApplicationContext(), scannedAppSet);

     HashSet<String> scannedAppSet = new HashSet<String>(); 
    scannedAppSet.add( val);*/



    public static void setScannedAppSet(Context context, Set<String> appList) {
            Editor editor = getDefaultPref(context).edit();
            editor.putStringSet(MG_SCAN_APP_LIST, appList);
            editor.commit();
        }

        public static Set<String> getScannedAppSet(Context context) {
            return getDefaultPref(context).getStringSet(MG_SCAN_APP_LIST, new HashSet<String>());
        }



    public static SharedPreferences getDefaultPref(Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        }
    
    
    

}
