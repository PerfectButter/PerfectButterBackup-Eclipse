package com.app.perfectbutterbackup;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// for any functionality that's needed for the backup tab
public class BackupTabFragment extends Fragment
{
	// every Fragment should have a blank constructor. Smashing Android UI page 265.
	public BackupTabFragment()
	{
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
        return inflater.inflate(R.layout.backuptabfragmentlayout, container, false);
	}
}