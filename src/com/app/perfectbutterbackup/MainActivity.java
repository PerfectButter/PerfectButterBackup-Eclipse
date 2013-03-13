package com.app.perfectbutterbackup;
import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.CompoundButton;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener 
{
	private static final int _ReqChooseFile = 0;
	private final static String TAG = "TestActivity";
	private String fileName = "";
	private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
	private static final String STATUS_BATTERY_ICON = "statusbar_battery_icon";
	private CheckBox batteryStatus, volumeManager;


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
     * Listener for Kernel options
     */
    public void onKernelRadioSelected(View view) {
      switch (view.getId()) {
        case R.id.kernel_battery:
          Log.i(TAG, "kernel battery option selected");
          toastMessage("Battery Mode Enabled");
          break;
          
        case R.id.kernel_stock:
          Log.i(TAG,"kernel stock option selected");
          toastMessage("Stock Mode Enabled");
          break;
          
        case R.id.kernel_performance:
          Log.i(TAG, "kernel performance option selected");
          toastMessage("Performance Enabled ZOOM! ZOOOOOOM!");
          break;
      }
    }
    
    public void onBatteryStatusSelected(View view) {
      
      batteryStatus = (CheckBox)findViewById(R.id.batterystatuscheckbox);
      if (((CheckBox) view).isChecked()) {
        toastMessage("Battery Status Mod Enabled");
        Settings.System.putInt(getContentResolver(), STATUS_BATTERY_ICON,4);
        }
      else {
        toastMessage("Battery Status Stock");
        Settings.System.putInt(getContentResolver(), STATUS_BATTERY_ICON, 0);
        }
    }
    
    public void onVolumeManagerSelected(View view) {
      
      volumeManager = (CheckBox)findViewById(R.id.volumemanagercheckbox);
      if(((CheckBox) view). isChecked()) {
        toastMessage("Volume Manager mod enabled");
        
      }
      else {
        toastMessage("Volume Manager mod Disabled");
      }
      
    }

	/*
	 * Listener for Navigation Bar sizing
	 */
    public void onNavigationRadioSelected(View view) {

      // Check which radio button was clicked
      switch (view.getId()) {
        case R.id.navigation_bar_small:
            Log.i(TAG, "Small navigation selected");
            Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT, 30);
            toastMessage("Small navigation Selected");
            break;
          
          case R.id.navigation_bar_medium:
            Log.i(TAG, "Medium navigation selected");
            Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT, 50);
            toastMessage("Medium Navigation selected");
            break;
            
          case R.id.navigation_bar_large:
            Log.i(TAG, "Large navigation selected");
            Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT, 80);
            toastMessage("Large Navigation selected");
            break;
      }
    }

	/*
	 * The onclick listener for the reboot device button from the rom control fragment
	 */
	public void rebootDevice(View v)
	{
		Builder userConfirmation = new AlertDialog.Builder(this);
        userConfirmation.setIcon(android.R.drawable.ic_dialog_alert);
        userConfirmation.setTitle("Confirm Reboot");
        userConfirmation.setMessage("Are you sure you wish to reboot your device?");
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
		Intent intent = new Intent(this, FileChooserActivity.class);
		intent.putExtra(FileChooserActivity._Rootpath, (Parcelable) new LocalFile("/your/path"));
		startActivityForResult(intent, _ReqChooseFile);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    switch (requestCode) 
	    {
		    case _ReqChooseFile:
		        if (resultCode == RESULT_OK) 
		        {
		            @SuppressWarnings("unchecked")
					List<LocalFile> files = (List<LocalFile>) data.getSerializableExtra(FileChooserActivity._Results);
		            for (File f : files)
		            {
		                System.out.println(f.getAbsolutePath());
		                this.fileName = f.getAbsolutePath();
		                EditText filePathTextBox = (EditText) findViewById(R.id.restoreTabFilePathTextBox);
		                filePathTextBox.setText(this.fileName);
		            }
		        }
		        break;
	    }
	}
}