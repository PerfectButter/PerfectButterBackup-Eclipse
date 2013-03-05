package com.app.perfectbutterbackup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

// for any functionality that's needed for the backup tab
public class ROMControlFragment extends Fragment {
  
  private static Toast toaster;
  private FragmentActivity fa;
  private final static String TAG = "TestActivity";

  private RadioGroup radioGroup2;
  private RadioButton medium_radio;
  
  

	// every Fragment should have a blank constructor. Smashing Android UI page 265.
	public ROMControlFragment()
	{
		
	}
	
	public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.i(TAG, "On Create .....");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {	  
	    
	    View v = inflater.inflate(R.layout.romcontroltabfragmentlayout, container, false);
	   	    
        return v;
	}
	
}