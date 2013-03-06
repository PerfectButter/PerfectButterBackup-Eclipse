package com.app.perfectbutterbackup;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// for any functionality that's needed for the backup tab
public class ROMControlFragment extends Fragment 
{
  private final static String TAG = "TestActivity";

  // every Fragment should have a blank constructor. Smashing Android UI page 265.
	public ROMControlFragment()
	{
		
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{
      super.onCreate(savedInstanceState);
      Log.i(TAG, "On Create .....");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{	  
	    View v = inflater.inflate(R.layout.romcontroltabfragmentlayout, container, false);
	   	    
        return v;
	}
}