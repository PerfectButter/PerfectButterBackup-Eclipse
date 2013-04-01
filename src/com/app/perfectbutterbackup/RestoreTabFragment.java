package com.app.perfectbutterbackup;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class RestoreTabFragment extends Fragment // for any functionality that's needed for the backup tab
{	
	
	
	public RestoreTabFragment() { } // every Fragment should have a blank constructor. Smashing Android UI page 265.

	// RESTORE FROM
	static RadioButton restoresdcardRadioButton;
	static RadioButton restoreDropBoxRadioButton;
	static RadioButton restoreEmailRadioButton;

	// RESTORE OPTIONS
	static CheckBox restoreCallLogCheckBox; 
	static CheckBox restoreSmsCheckBox;
	static CheckBox restoreAppsCheckBox;
	static CheckBox restoreAppDataCheckBox;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
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
	
	public static void runRestore(String password)
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
		case DROPBOX:
			throw new RuntimeException("Dropbox not implemented yet");

		case EMAIL:
			throw new RuntimeException("Dropbox not implemented yet");
		case MULTIPLE_FILE_ON_SDCARD:

			for (int i = 0; i < filesToRestore.size(); i++) {
				BackupTabFragment.runLinuxCopyCommand(filesToRestore.get(i),
						filesDestination.get(i));
			}
			break;

		case TAR_FILE_ON_SDCARD:
			String tarCommand = "tar -x -C / -f /sdcard/perfectButterBackup.tar";
			for (int i = 0; i < filesDestination.size(); i++) {
				String file = filesDestination.get(i);
				file = file.substring(1); // remove '/' from the begining
				tarCommand += " " + file;
			}
			BusyBox.exec(tarCommand);
			break;

		}
		
	}
}