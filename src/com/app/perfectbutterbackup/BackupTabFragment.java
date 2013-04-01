package com.app.perfectbutterbackup;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
	
	private static void saveToDropBox(String string) {
		// TODO: Implement this method
	}

	private static void sendEmailWithFileAttachment(String string) {
		// TODO: Implement this method
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