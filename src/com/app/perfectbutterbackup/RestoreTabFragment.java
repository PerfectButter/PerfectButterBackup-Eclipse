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
	
	static CheckBox restoreCallLogCheckBox; 
	static CheckBox restoreSmsCheckBox;
	static RadioButton restoresdcardRadioButton;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.restoretabfragmentlayout, container, false);
		restoreCallLogCheckBox = (CheckBox) v.findViewById(R.id.restorePhoneLogCheckBox); 
		restoreSmsCheckBox = (CheckBox) v.findViewById(R.id.restoreTextMessagesCheckBox);
		restoresdcardRadioButton = (RadioButton) v.findViewById(R.id.restoreLocationSDCardRadioButton);
		return v;
		
	}
	
	public static void runRestore(String password)
	{
		ArrayList<String> filesToRestore = new ArrayList<String>();
		ArrayList<String> filesDestination = new ArrayList<String>();
		if(restoresdcardRadioButton.isChecked())
		{
			
		
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
		
		/*
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/ApplicationCache.db","/data/data/com.android.browser/app_appcache/ApplicationCache.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/Databases.db","/data/data/com.android.browser/app_databases/Databases.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/CachedGeoposition.db","/data/data/com.android.browser/app_geolocation/CachedGeoposition.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/WebpageIcons.db","/data/data/com.android.browser/app_icons/WebpageIcons.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/autofill.db","/data/data/com.android.browser/databases/autofill.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/browser2.db","/data/data/com.android.browser/databases/browser2.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/webview.db","/data/data/com.android.browser/databases/webview.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/webviewCookiesChromium.db","/data/data/com.android.browser/databases/webviewCookiesChromium.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/webviewCookiesChromiumPrivate.db","/data/data/com.android.browser/databases/webviewCookiesChromiumPrivate.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/alarms.db","/data/data/com.android.deskclock/databases/alarms.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/EmailProvider.db","/data/data/com.android.email/databases/EmailProvider.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/EmailProviderBackup.db","/data/data/com.android.email/databases/EmailProviderBackup.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/EmailProviderBody.db","/data/data/com.android.email/databases/EmailProviderBody.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/grants.db","/data/data/com.android.keychain/databases/grants.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/launcher.db","/data/data/com.android.launcher/databases/launcher.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/calendar.db","/data/data/com.android.providers.calendar/databases/calendar.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/contacts2.db","/data/data/com.android.providers.contacts/databases/contacts2.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/profile.db","/data/data/com.android.providers.contacts/databases/profile.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/downloads.db","/data/data/com.android.providers.downloads/databases/downloads.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/external.db","/data/data/com.android.providers.media/databases/external.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/internal.db","/data/data/com.android.providers.media/databases/internal.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/settings.db","/data/data/com.android.providers.settings/databases/settings.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/mmssms.db","/data/data/com.android.providers.telephony/databases/mmssms.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/telephony.db","/data/data/com.android.providers.telephony/databases/telephony.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/user_dict.db","/data/data/com.android.providers.userdictionary/databases/user_dict.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/qsb-log.db","/data/data/com.android.quicksearchbox/databases/qsb-log.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/webview.db","/data/media/0/webview.db");

		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/locksettings.db","/data/system/locksettings.db");
		BackupTabFragment.runLinuxCopyCommand("/sdcard/perfectButterBackup/accounts.db","/data/system/users/0/accounts.db");
		*/
	}
}