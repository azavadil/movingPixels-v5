/*
Copyright (c) 2014 Lawrence Angrave
Dual licensed under Apache2.0 and MIT Open Source License (included below): 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package apps101.movingpixels;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Settings activity extends PreferenceActivity 


public class SettingsActivity extends PreferenceActivity {
	
	
	// Don't forget to create onCreate method
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// In here we can create all of the layouts directly from the xml
		// how do we create a preference screen from the resource? 
		//
		// $ addPreferencesFromResource(R.xml.penguin_prefs)
		
	
		// addPreferencesFromResources has been deprecated. 
		// here we identify the version of android 
		// and only call addPreferencesFromResources if we have an old OS
		
		// we use the build number to determine the which version we're working with
		
		// savedInstanceState: remember that android likes to recreate the objects
		//                     when it recreates the objects it gives them a chance to 
		//                     reuse data on the previous instance.
		//                     That's handy when you rotate the device and you want the 
		//                     device to remember what in the text input box
		
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			showPreferencesPreHoneycomb();
		} else {
			showPreferencesFragmentStyle(savedInstanceState);
		}
	}


	// we're using the fragment and fragment manager that come directly with android
	// not the fragment manager that comes with the support library. 
	// why: for early devices we don't run this code. 
    // we know that's ok because we have a conditional that makes sure this 
	// code is only run on new devices 
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void showPreferencesFragmentStyle(Bundle savedInstanceState) {
		
		// we only want to change the fragment the first time (i.e. the savedInstanceState is null)
		// if the savedInstanceState is not null then we're recreating the fragment from a stored
		// state, and the framework does that for us automatically
		
		if (savedInstanceState == null) {
			
			// we get fragment manager
			// similar to sharedPreferences
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			
			// we make the class MyPreferencesFragement 
			Fragment fragment = new MyPreferencesFragment();
			
			// with a fragment we either want to add, replace, or remove
			// we could have a layout to use as the container. 
			// if we're using the whole screen there's already a container that works
			// android.R.id.content.
			// you can think of android.R.id.content as the secret part of the xml
			// that we don't write. There's an enclosing frame layout that everything
			// we write sits inside
			// The contents of the enclosing container will just be the fragment we created. 
			transaction.replace(android.R.id.content, fragment);
			
			//every time we see a transaction we expect to end with a commit
			transaction.commit();
		}

	}
	
	

	@SuppressWarnings("deprecation")
	private void showPreferencesPreHoneycomb() {
		Log.d("Hurrah","Pre-Honeycomb!");
		
		// the method is crossed out because the method has been deprecated
		// 
		
		addPreferencesFromResource(R.xml.penguin_prefs);
	}

	// we are making this class from scratch
	// this extends an android class PreferenceFragment
	// fragment life-cycle.

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class MyPreferencesFragment extends PreferenceFragment {
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			Log.d("F", "I'm attached to an activity - I have a context!");
		}

		// we could take our penguin view and stick it in our fragment
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			// we use addPreferencesFromResources to add all the views to the window
			// 
			this.addPreferencesFromResource(R.xml.penguin_prefs);
			return super.onCreateView(inflater, container, savedInstanceState);
		}
	};
}
