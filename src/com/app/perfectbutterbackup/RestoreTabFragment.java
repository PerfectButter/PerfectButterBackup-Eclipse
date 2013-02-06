package com.app.perfectbutterbackup;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


// for any functionality that's needed for the backup tab
public class RestoreTabFragment extends ListFragment
{
	// every Fragment should have a blank constructor. Smashing Android UI page 265.
	public RestoreTabFragment()
	{
			
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		TextView textView = new TextView(getActivity());
		textView.setGravity(Gravity.CENTER);
		textView.setText("restore manager");
		return textView;
	}
}