package com.app.perfectbutterbackup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

// for any functionality that's needed for the backup tab
public class BackupTabFragment extends Fragment
{
	public static String destinationPath = "/sdcard/perfectButterBackup/";
	static Fragment sContext;
	
	
	public BackupTabFragment() { } 	// every Fragment should have a blank constructor. Smashing Android UI page 265.

	
	// BACKUP LOCATION
	static RadioButton sdcardRadioButton;
	static RadioButton dropboxRadioButton;
	static RadioButton emailRadioButton;

	// BACKUP OPTIONS
	static CheckBox callLogCheckBox; 
	static CheckBox smsCheckBox;
	static CheckBox appsCheckBox;
	static CheckBox appsDatgaCheckBox;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		sContext = this;
		// initialize busybox
		BusyBox.installBusyBoxIfNecessary(container.getContext());
		
		View v = inflater.inflate(R.layout.backuptabfragmentlayout, container, false);
		
		// BACKUP LOCATION
		sdcardRadioButton = (RadioButton) v.findViewById(R.id.backupLocationSDCardRadioButton);
		dropboxRadioButton = (RadioButton) v.findViewById(R.id.backupLocationDropboxRadioButton);
		emailRadioButton = (RadioButton) v.findViewById(R.id.backupLocationEmailRadioButton);
		
		// BACKUP OPTION
		callLogCheckBox = (CheckBox) v.findViewById(R.id.backupPhoneLogCheckBox);
		smsCheckBox = (CheckBox) v.findViewById(R.id.backupTextMessagesCheckBox);
		appsCheckBox = (CheckBox) v.findViewById(R.id.backupAppsCheckBox);
		appsDatgaCheckBox = (CheckBox) v.findViewById(R.id.backupAppDataCheckBox);
		 
		return v; 
	}
	
	public static void runBackup(String password)
	{
		
		ArrayList<String> itemsToBackup = new ArrayList<String>();
				
		////////////////////////////////////////
		// BACKUP LOCATION
		if (sdcardRadioButton.isChecked()) {
			Globals.sBackupMedia = BackupMedia.TAR_FILE_ON_SDCARD;
			
		} else if (dropboxRadioButton.isChecked()) {
			Globals.sBackupMedia = BackupMedia.DROPBOX;
			
		} else if (emailRadioButton.isChecked()) {
			Globals.sBackupMedia = BackupMedia.EMAIL;
		}
		////////////////////////////////////////		
			

		////////////////////////////////////////
		// DETERMINE ITEMS TO SAVE 
		// BASED ON BACKUP OPTIONS
		if (callLogCheckBox.isChecked()) {
			itemsToBackup.add("/data/data/com.android.providers.telephony/databases/telephony.db");
			itemsToBackup.add("/data/data/com.android.providers.contacts/databases/contacts2.db");
		}
		if (smsCheckBox.isChecked()) {
			itemsToBackup.add("/data/data/com.android.providers.telephony/databases/mmssms.db");
		}
		if (appsCheckBox.isChecked()) {
			itemsToBackup.add("/data/app");
		}
		if (appsDatgaCheckBox.isChecked()) {
			// BACKUP EVERYTHING
			itemsToBackup.clear();

			// only a single "/data" is enogh to single every single file in the
			// data directory. Do other individual ones are needed while we are 
			// using this one
			itemsToBackup.add("/data");
		}
		//////////////////////////////////////////
			

		
		switch(Globals.sBackupMedia)
		{
		
		case MULTIPLE_FILE_ON_SDCARD:
			BackupTabFragment.makeDirectoryInsdCard();
			for(int i=0; i< itemsToBackup.size() ; i++)
			{
				runLinuxCopyCommand(itemsToBackup.get(i), destinationPath);
			}
			break;
		

		case TAR_FILE_ON_SDCARD:
		case DROPBOX:	
		case EMAIL:

			/////////////////////////
			// TAR UP ITEMS
			String tarCommand = "tar -cf /sdcard/perfectButterBackup.tar";
			for(int i=0; i<itemsToBackup.size(); i++)
			{
				tarCommand += " " + itemsToBackup.get(i);
			}
			BusyBox.exec(tarCommand);
			
			
			if(Globals.sBackupMedia==BackupMedia.EMAIL) {
				sendEmailWithFileAttachment("/sdcard/perfectButterBackup.tar");
			}
			if(Globals.sBackupMedia==BackupMedia.DROPBOX) {
				saveToDropBox("/sdcard/perfectButterBackup.tar");
			}
			break;
		
		}
	}
	
	private static void saveToDropBox(String filename) {
		File file = new File(filename);
		if (!file.exists() || !file.canRead()) {
		    //Toast.makeText(sContext, "Attachment Error", Toast.LENGTH_SHORT).show();
		    return;
		}
		
		// got dropbox example from this URL
		// URL: https://forums.dropbox.com/topic.php?id=32222
		
		final Intent DBIntent = new Intent(android.content.Intent.ACTION_SEND);
		DBIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		DBIntent.setType("*/*");
		DBIntent.setPackage("com.dropbox.android");
		sContext.startActivity(DBIntent);
	}

	private static void sendEmailWithFileAttachment(String filename) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"farooqnida@hotmail.com"});
		intent.putExtra(Intent.EXTRA_SUBJECT, "Perfect Butter Backup");
		intent.putExtra(Intent.EXTRA_TEXT, "Hi,\n\nPlease find perfect butter backup file in the attachment.\n\nThanks\n\nPerfect Butter Team");
		File file = new File(filename);
		if (!file.exists() || !file.canRead()) {
		    //Toast.makeText(sContext, "Attachment Error", Toast.LENGTH_SHORT).show();
		    return;
		}
		Uri uri = Uri.parse("file://" + file);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		sContext.startActivity(Intent.createChooser(intent, "Send email..."));
	}

	public static void makeDirectoryInsdCard()
	{
		try{
			  Runtime.getRuntime().exec("/system/xbin/su -c mkdir /sdcard/perfectButterBackup");
		}
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void runLinuxCopyCommand(String fileName, String destinationPath)
	{
		try{
			  Runtime.getRuntime().exec(new String[]{"/system/xbin/su","-c", "cp", fileName, destinationPath});
		}
		catch (IOException e) { e.printStackTrace(); }
	}	
}