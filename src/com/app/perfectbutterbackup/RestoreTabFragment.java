package com.app.perfectbutterbackup;



import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RestoreTabFragment extends Fragment // for any functionality that's needed for the backup tab
{	
	
	
	public RestoreTabFragment() { } // every Fragment should have a blank constructor. Smashing Android UI page 265.

	public  Handler h = new Handler(); 	
	static Fragment sContext;
	// RESTORE FROM
	static RadioButton restoresdcardRadioButton;
	static RadioButton restoreDropBoxRadioButton;
	static RadioButton restoreEmailRadioButton;

	// RESTORE OPTIONS
	static CheckBox restoreCallLogCheckBox; 
	static CheckBox restoreSmsCheckBox;
	static CheckBox restoreAppsCheckBox;
	static CheckBox restoreAppDataCheckBox;
	
	public static String dropboxFileDestinationPath = "/sdcard/perfectButterBackup.tar";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		sContext = this;
		View v = inflater.inflate(R.layout.restoretabfragmentlayout, container, false);
		
		// RESTORE FROM
		restoresdcardRadioButton  = (RadioButton) v.findViewById(R.id.restoreLocationSDCardRadioButton);
		restoreDropBoxRadioButton = (RadioButton) v.findViewById(R.id.restoreLocationDropboxRadioButton);
		restoreEmailRadioButton   = (RadioButton) v.findViewById(R.id.restoreLocationEmailRadioButton);
		
		// RESTORE OPTIONS
		restoreCallLogCheckBox    = (CheckBox)    v.findViewById(R.id.restorePhoneLogCheckBox); 
		restoreSmsCheckBox        = (CheckBox)    v.findViewById(R.id.restoreTextMessagesCheckBox);
		restoreAppDataCheckBox    = (CheckBox)    v.findViewById(R.id.restoreAppDataCheckBox); 
		restoreAppsCheckBox       = (CheckBox)    v.findViewById(R.id.restoreAppsCheckBox);
		
		return v;
		
	}
	
	public int CheckWhichRadioButton()
	{
		int sdcard = 0;
		int dropBox = 1;
		int email = 2;
		int nothing = 10;
		
		if(restoresdcardRadioButton.isChecked())
		{
			return sdcard;
		}
		
		else if(restoreDropBoxRadioButton.isChecked())
		{
			return dropBox;
		}
		
		else if(restoreEmailRadioButton.isChecked())
		{
			return email;
		}
		
		else 
		{
			return nothing;
		}
		
	}
	
	public static void runRestore()
	{
		ArrayList<String> filesToRestore = new ArrayList<String>();
		ArrayList<String> filesDestination = new ArrayList<String>();

		/////////////////////////////////////
		// RESTORE FROM
		if (restoresdcardRadioButton.isChecked()) {
			Globals.sBackupMedia = BackupMedia.TAR_FILE_ON_SDCARD;
			
		} else if (restoreDropBoxRadioButton.isChecked()) {
			Globals.sBackupMedia = BackupMedia.DROPBOX;
			
		} else if (restoreEmailRadioButton.isChecked()) {
			Globals.sBackupMedia = BackupMedia.EMAIL;
		}
		
		
		
		//////////////////////////////////////
		// RESTORE OPTIONS
		if (restoreCallLogCheckBox.isChecked())
		{
			filesToRestore.add("/sdcard/perfectButterBackup/telephony.db");
			filesDestination.add("/data/data/com.android.providers.telephony/databases/telephony.db");
			
			filesToRestore.add("/sdcard/perfectButterBackup/contacts2.db");
			filesDestination.add("/data/data/com.android.providers.contacts/databases/contacts2.db");
		}
		if(restoreSmsCheckBox.isChecked())
		{
			filesToRestore.add("/sdcard/perfectButterBackup/mmssms.db");
			filesDestination.add("/data/data/com.android.providers.telephony/databases/mmssms.db");
		}
		if(restoreAppsCheckBox.isChecked())
		{
			filesDestination.add("/data/app");
		}
		if(restoreAppDataCheckBox.isChecked())
		{
			filesDestination.add("/data");
		}
		

		
		
		switch (Globals.sBackupMedia) {
		

		
			
		case MULTIPLE_FILE_ON_SDCARD:

			for (int i = 0; i < filesToRestore.size(); i++) {
				BackupTabFragment.runLinuxCopyCommand(filesToRestore.get(i),
						filesDestination.get(i));
			}
			break;
		case EMAIL:	
		case DROPBOX:
		case TAR_FILE_ON_SDCARD:
			String tarCommand = "tar -x -C / -f /sdcard/perfectButterBackup.tar";
			for (int i = 0; i < filesDestination.size(); i++) {
				String file = filesDestination.get(i);
				file = file.substring(1); // remove '/' from the begining
				tarCommand += " " + file;
				
			}
			
			BusyBox.exec(tarCommand);
			// busybox  tar -x -C / -f /sdcard/perfectButterBackup.tar data/data/com.android.providers.telephony/databases/mmssms.db .....
			break;

		}
		
		 

		
	}
	
	private static final int DROPBOXFILE_SELECT_CODE = 0;
	private static final int  FILESYSTEM_SELECT_CODE = 1;

    public static void showFileChooserFromDropBox() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
        intent.setType("*/*"); 
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
        	Log.w("Nidha", "here 1");
            sContext.startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Download"),
                    DROPBOXFILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
        //    Toast.makeText(this, "Please install a File Manager.", 
             //       Toast.LENGTH_SHORT).show();
        }
    }
    
    public static void showFileChooserFromFileSystem() {

        Intent intent = new Intent(sContext.getActivity(), FileChooserActivity.class);
        intent.putExtra(FileChooserActivity._Rootpath, (Parcelable) new LocalFile("/your/path"));
        sContext.startActivityForResult(intent, FILESYSTEM_SELECT_CODE);
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Log.w("Nidha", "here 2");
		switch (requestCode) {
		case DROPBOXFILE_SELECT_CODE:
			if (resultCode == Activity.RESULT_OK) {
				// Get the Uri of the selected file
				// result_ok means that user picked up the file
				Uri uri = data.getData();
				
				Log.w("Nidha", "here 3");
				ContentResolver cr = sContext.getActivity().getContentResolver();
				try {
					InputStream istream = cr.openInputStream(uri);
					File f = new File(dropboxFileDestinationPath);
					OutputStream out = new FileOutputStream(f);
					byte buf[]=new byte[1024];
					int len;
					 while((len = istream.read(buf))>0)
					 {
						 out.write(buf,0,len);
						 
					 }
					 //String fileName = f.getAbsolutePath();
					 out.close();
					 istream.close();
					 
					 Log.w("RestoreNida", "the stream are closed");
					 //BackupTabFragment.runLinuxCopyCommand(fileName, dropboxFileDestinationPath);
					 //System.out.println("now the file from dropbox is saved to sdcard");
					// askForPassword(sContext.getActivity());  ( IF  RESTORE DOESNT WORK UNCOMMENT THIS LINE)
					 runRestore();
					 
					 
					// TODO: read from istream & produce "/sdcard/perfectButterBackup.tar"
					// example available at
					// http://www.roseindia.net/java/java-conversion/InputstreamToFile.shtml
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
				
				
			}
			
			
			break;
			
		case FILESYSTEM_SELECT_CODE:
			if (resultCode == Activity.RESULT_OK) 
            {
                @SuppressWarnings("unchecked")
                List<LocalFile> files = (List<LocalFile>) data.getSerializableExtra(FileChooserActivity._Results);
                for (File f : files)
                {
                    System.out.println(f.getAbsolutePath());
       //             this.fileName = f.getAbsolutePath();
                    Log.w("Nidha", "here10 ");
                    
                    BusyBox.exec("rm " + dropboxFileDestinationPath);
                    BusyBox.exec("mv " + f.getAbsolutePath() + " " + dropboxFileDestinationPath);
//                	BackupTabFragment.runLinuxCopyCommand(f.getAbsolutePath(),
//                			dropboxFileDestinationPath);
                	//askForPassword(sContext.getActivity());
                	runRestore();
                    
                }
            }
            break;
			
		}

		super.onActivityResult(requestCode, resultCode, data);
	}   
	
	public void askForPassword(Activity activity)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setTitle("Restore Password?");
		alert.setMessage("Please enter the password for the archived backup.");

		final EditText input = new EditText(activity);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				  Editable value = input.getText();
				  MainActivity.PASSWORD = value.toString();
//				  RestoreTabFragment.runRestore(MainActivity.PASSWORD);
			}});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		{
			  public void onClick(DialogInterface dialog, int whichButton) 
			  {
				  MainActivity.PASSWORD = "";
	//			  RestoreTabFragment.runRestore(MainActivity.PASSWORD);
			  }
		});

		alert.show();
	}
    
    
}