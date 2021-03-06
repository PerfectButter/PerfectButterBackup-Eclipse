package com.app.perfectbutterbackup;
import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;

import java.io.DataOutputStream;
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
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener 
{
	private static final int _ReqChooseFile = 0;
	private final static String TAG = "TestActivity";
	private String fileName = "";
	private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
	private static final String NAVIGATION_BAR_WIDTH = "navigation_bar_width";
    private static final String NAVIGATION_BAR_HEIGHT_LANDSCAPE = "navigation_bar_height_landscape";
	private static final String STATUS_BATTERY_ICON = "statusbar_battery_icon";
	static String PASSWORD = "";
	private static final String STATUSBAR_BATTERY_ICON = "statusbar_battery_icon";
	private static final String ENABLE_VOLUME_OPTIONS = "enable_volume_options";
	private CheckBox batteryStatus, volumeManager;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ActionBar actionBar = getActionBar(); 		// Set up the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager()); 		// Create the adapter that will return a fragment for each of the three primary sections of the app.
		mViewPager = (ViewPager) findViewById(R.id.pager); 		// Set up the ViewPager with the sections adapter.
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() // When swiping between different sections, select the corresponding tab. We can also use ActionBar.Tab#select() to do this if we have a reference to the Tab.
		{
			@Override
			public void onPageSelected(int position) 
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) { 		// For each of the sections in the app, add a tab to the action bar.
			// Create a tab with text corresponding to the page title defined by the adapter. Also specify this Activity object, which implements the TabListener interface, as the callback (listener) for when this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main, menu); 		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		mViewPager.setCurrentItem(tab.getPosition()); 		// When the given tab is selected, switch to the corresponding page in the ViewPager.
	}

	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { 	}

	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { 	}
	
	/*
	 * Method called to toast messages
	 */
	public void toastMessage(String message) { Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();	}
	
	/**
	 * Change the Battery icon in notification bar
	 * @param view is the current view
	 */
    public void onBatteryStatusSelected(View view) {
      
      batteryStatus = (CheckBox)findViewById(R.id.batterystatuscheckbox);
      if (((CheckBox) view).isChecked()) {
        toastMessage("Battery Status Mod Enabled");
        Settings.System.putInt(getContentResolver(), STATUSBAR_BATTERY_ICON,4);
        }
      else {
        toastMessage("Battery Status Stock");
        Settings.System.putInt(getContentResolver(), STATUSBAR_BATTERY_ICON, 0);
        }
    }
    
    /**
     * Activate or disable the Volume Manager Modification
     * @param view is the current view
     */
    public void onVolumeManagerSelected(View view) {
      
      volumeManager = (CheckBox)findViewById(R.id.volumemanagercheckbox);
      if(((CheckBox) view). isChecked()) {
        toastMessage("Volume Manager mod enabled");
        Settings.System.putInt(getContentResolver(), ENABLE_VOLUME_OPTIONS,1);
        
      }
      else {
        toastMessage("Volume Manager mod Disabled");
        Settings.System.putInt(getContentResolver(), ENABLE_VOLUME_OPTIONS,0);
      }
      
    }
	
	/*
	 * Listener for Navigation Bar sizing
	 */
	public void onNavigationRadioSelected(View view) {
	
	  // Check which radio button was clicked
	  switch (view.getId()) 
	  {
	    case R.id.navigation_bar_small:
	        Log.d(TAG, "Small navigation selected");
	    int height = getResources().getDimensionPixelSize(R.dimen.navigation_bar_24);
	    Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT, height);
	    Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_WIDTH, height);
	    toastMessage("Small navigation Selected");
	    break;
	  
	  case R.id.navigation_bar_medium:
	    Log.d(TAG, "Medium navigation selected");
	    height = getResources().getDimensionPixelSize(R.dimen.navigation_bar_36);
	    Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT, height);
	    Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_WIDTH, height);
	    toastMessage("Medium Navigation selected");
	    break;
	    
	  case R.id.navigation_bar_large:
	    Log.d(TAG, "Large navigation selected");
	    height = getResources().getDimensionPixelSize(R.dimen.navigation_bar_48);
	    Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT, height);
	    Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_WIDTH, height);
	    toastMessage("Large Navigation selected");
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

		public Fragment getItem(int position) // getItem is called to instantiate the fragment for the given page.
		{
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
	
	/*
	 * The onclick listener for the reboot device button from the rom control fragment
	 */
	public void onRebootDevice(View v)
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
	
	public void onBootAnimationSelected(View view)
    {
    	android.widget.CheckBox checkBox = (android.widget.CheckBox) view;

		String[] commands;  
    	if(checkBox.isChecked())
    		commands = new String[]{"mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system", "cp /system/media/customBootAnimation.zip /system/media/bootanimation.zip", "chmod 664 /system/media/bootanimation.zip"};
    	else
    		commands = new String[]{"mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system", "cp /system/media/stockBootAnimation.zip /system/media/bootanimation.zip", "chmod 664 /system/media/bootanimation.zip"};
    	
    	this.RunAsRoot(commands);
    }
	
	
	 public void RunAsRoot(String[] cmds)
	 {
		 try
		 {
	         Process p = Runtime.getRuntime().exec("/system/xbin/su");
	         DataOutputStream os = new DataOutputStream(p.getOutputStream());            
	         for (String tmpCmd : cmds) 
	         {
	             os.writeBytes(tmpCmd+"\n");
	         }           
	         os.writeBytes("exit\n");  
	         os.flush();
		 }
		 catch (IOException e) { e.printStackTrace(); }
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
	
	public void onRunBackupClicked(View v)
	{
	    BackupTabFragment.runBackup();
	    
	}
	
	
	public void onRestoreBackupClicked(View v)
	{
		/*
	 	int sdcard = 0;
		int dropBox = 1;
		int email = 2;
		int nothing = 10;
		 */
	RestoreTabFragment toCheck = new RestoreTabFragment();
	
	 int checkNumber = toCheck.CheckWhichRadioButton();
	 if(checkNumber == 0)
	 {
		 // save from sdcard;
		RestoreTabFragment.runRestore();
		 
	 }
	 if(checkNumber == 1)
	 {
		 RestoreTabFragment.showFileChooserFromDropBox();
	 }
	 
	 if(checkNumber == 2)
	 {
		 RestoreTabFragment.showFileChooserFromFileSystem();
	 }
	 
	 if(checkNumber == 10)
	 {
		 //throw error message saying but it shldnt reach this point
	 }
	 
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode,  resultCode,  data);
		
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
//		                EditText filePathTextBox = (EditText) findViewById(R.id.restoreTabFilePathTextBox);
//		                filePathTextBox.setText(this.fileName);
		            }
		        }
		        break;
	    }
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	}

	@Override
	public void onResume() {
	    super.onResume();
	}

	private void save(final boolean isChecked) {
	    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putBoolean("check", isChecked);
	    editor.commit();
	}

	private boolean load() { 
	    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
	    return sharedPreferences.getBoolean("check", false);
	}
	
}
