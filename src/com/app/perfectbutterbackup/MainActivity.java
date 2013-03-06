package com.app.perfectbutterbackup;
import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener 
{
	private static final int _ReqChooseFile = 0;
	private FragmentActivity fa;
	private final static String TAG = "TestActivity";


	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		askRoot();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() 
		{
			@Override
			public void onPageSelected(int position) 
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		
	}
	
	/*
	 * Method called to toast messages
	 */
	public void toastMessage(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
		
	}
	
	/*
	 * Will prompt to ask for Root access so we can access system files
	 * We can actually just run p = Runtime.getRuntime().exec("su");
	 * This just toasts messages to test
	 */
	public void askRoot() 
	{
		Process p;
		try {
		   // Preform su to get root privledges
		   p = Runtime.getRuntime().exec("su"); 
	
		   // Attempt to write a file to a root-only
		   DataOutputStream os = new DataOutputStream(p.getOutputStream());
		   os.writeBytes("echo \"Do I have root?\" >/sdcard/temporary.txt\n");
	
		   // Close the terminal
		   os.writeBytes("exit\n");
		   os.flush();
		   try {
		      p.waitFor();
		           if (p.exitValue() != 255) {
		        	  // TODO Code to run on success
		              toastMessage("root");
		           }
		           else {
		        	   // TODO Code to run on unsuccessful
		        	   toastMessage("not root");
		           }
		   } catch (InterruptedException e) {
		      // TODO Code to run in interrupted exception
			   toastMessage("not root");
		   }
		} catch (IOException e) 
		{
		   // TODO Code to run in input/output exception
			toastMessage("not root");
		}
	}
	
	
	public void rebootPhone(View v)
	{
		Builder userConfirmation = new AlertDialog.Builder(this);
        userConfirmation.setIcon(android.R.drawable.ic_dialog_alert);
        userConfirmation.setTitle("Closing Activity");
        userConfirmation.setMessage("Are you sure you want to close this activity?");
        userConfirmation.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
        	public void onClick(DialogInterface dialog, int which) 
        	{ 
        		finish();
    			try{
    				Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c","reboot now"});
		  		}
		  		catch (IOException e) {} 
		  		
		  		try{
		  			  Runtime.getRuntime().exec(new String[]{"/system/xbin/su","-c","reboot now"});
		  		}
		  		catch (IOException e) {}
  		
		  		try{
		  			  Runtime.getRuntime().exec(new String[]{"su","-c","reboot now"});
		  		}
		  		catch (IOException e) {}
        	} });
        userConfirmation.setNegativeButton("No", null);
        userConfirmation.show();
	}
	
	
	/*
	 * The onclick listener for the 'choose file' button in the restoretabfragmentlayout.xml
	 */
	public void launchFileChooser(View v)
	{
		final Activity currentActivity = this;

		Intent intent = new Intent(currentActivity, FileChooserActivity.class);
		/*
		 * by default, if not specified, default rootpath is sdcard,
		 * if sdcard is not available, "/" will be used
		 */
		intent.putExtra(FileChooserActivity._Rootpath, (Parcelable) new LocalFile("/your/path"));
		startActivityForResult(intent, _ReqChooseFile);	
	    IFileProvider.FilterMode filterMode = (IFileProvider.FilterMode) intent.getSerializableExtra(FileChooserActivity._FilterMode);
	    boolean saveDialog = intent.getBooleanExtra(FileChooserActivity._SaveDialog, false);
	    List<LocalFile> files = (List<LocalFile>) intent.getSerializableExtra(FileChooserActivity._Results);
	    System.out.println("hi");
	    EditText editTextBox = (EditText) this.findViewById(R.id.restoreTabFilePathTextBox);
	    editTextBox.setText(files.get(0).getAbsolutePath(), BufferType.SPANNABLE);
	}
	
	public void onRadioButtonClicked(View view) {
      // Is the button now checked?
      boolean checked = ((RadioButton) view).isChecked();

      // Check which radio button was clicked
      switch (view.getId()) {
        case R.id.navigation_bar_small:
          if (checked) {
            Log.i(TAG, "Small navigation selected");
            toastMessage("Small navigation Selected");
          }
          break;
          
          case R.id.navigation_bar_medium:
          if (checked) {
            Log.i(TAG, "Medium navigation selected");
            toastMessage("Medium Navigation selected");
          }
          case R.id.navigation_bar_large:
          if (checked) {
            Log.i(TAG, "Large navigation selected");
            toastMessage("Large Navigation selected");
          }
          break;
    
      }
  }
	
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter 
	{
		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		public Fragment getItem(int position) 
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if(position==1) // the 0th tab in array indexes, or 1st tab in human indexes
			{
				BackupTabFragment btf = new BackupTabFragment();
				Fragment f = (Fragment) btf;
				return f;
			}
			else if(position==2)
			{
				RestoreTabFragment rtf = new RestoreTabFragment();
				Fragment f = (Fragment) rtf;
				return f;				
			}
//			else if(position==0)
//			{
//		
//			}
			else
			{
				ROMControlFragment rcf = new ROMControlFragment();
				Fragment f = (Fragment) rcf;
				return f;	
			}
		}

		@Override
		public int getCount() 
		{
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) 
		{
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(Locale.getDefault());
			case 1:
				return getString(R.string.title_section2).toUpperCase(Locale.getDefault());
			case 2:
				return getString(R.string.title_section3).toUpperCase(Locale.getDefault());
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment 
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() 
		{
			// empty!
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			// Create a new TextView and set its text to the fragment's section number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return textView;
		}
	}
}