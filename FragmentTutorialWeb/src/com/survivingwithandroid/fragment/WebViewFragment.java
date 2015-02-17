package com.survivingwithandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/*
 * Copyright (C) 2012-2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class WebViewFragment extends Fragment {

	private String currentURL;
	
	public void init(String url) {
		currentURL = url;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d("SwA", "WVF onCreateView");
		View v = inflater.inflate(R.layout.web_layout, container, false);
		if (currentURL != null) {
			Log.d("SwA", "Current URL  1["+currentURL+"]");
			
			WebView wv = (WebView) v.findViewById(R.id.webPage);
			wv.getSettings().setJavaScriptEnabled(true);
			wv.setWebViewClient(new SwAWebClient());
			wv.loadUrl(currentURL);
		    
		    
			
			
		}
		return v;
	}
	
	public void updateUrl(String url) {
		Log.d("SwA", "Update URL ["+url+"] - View ["+getView()+"]");
		currentURL = url;

		WebView wv = (WebView) getView().findViewById(R.id.webPage);
		wv.getSettings().setJavaScriptEnabled(true);
	    wv.loadUrl(url);

	}
	
	private class SwAWebClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}
		
	}
	
	

}
