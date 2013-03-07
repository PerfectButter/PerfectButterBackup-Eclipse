package com.app.perfectbutterbackup;

import java.io.IOException;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

// for any functionality that's needed for the backup tab
public class RestoreTabFragment extends Fragment
{	
	// every Fragment should have a blank constructor. Smashing Android UI page 265.
	public RestoreTabFragment()
	{
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
        //return inflater.inflate(R.layout.restoretabfragmentlayout, container, false);
		
		View v = inflater.inflate(R.layout.restoretabfragmentlayout, container, false);
		Button restoreButton = (Button) v.findViewById(R.id.restoreTabRunRestoreButton);
		restoreButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("the restore button works");
//				runLinuxRestoreCommand("/sdcard/perfectButterBackup/EmailProvider.db","/data/data/com.android.email/databases/EmailProvider.db");
				//check command : ls -l destination

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/ApplicationCache.db","/data/data/com.android.browser/app_appcache/ApplicationCache.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/Databases.db","/data/data/com.android.browser/app_databases/Databases.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/CachedGeoposition.db","/data/data/com.android.browser/app_geolocation/CachedGeoposition.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/WebpageIcons.db","/data/data/com.android.browser/app_icons/WebpageIcons.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/autofill.db","/data/data/com.android.browser/databases/autofill.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/browser2.db","/data/data/com.android.browser/databases/browser2.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/webview.db","/data/data/com.android.browser/databases/webview.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/webviewCookiesChromium.db","/data/data/com.android.browser/databases/webviewCookiesChromium.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/webviewCookiesChromiumPrivate.db","/data/data/com.android.browser/databases/webviewCookiesChromiumPrivate.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/alarms.db","/data/data/com.android.deskclock/databases/alarms.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/EmailProvider.db","/data/data/com.android.email/databases/EmailProvider.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/EmailProviderBackup.db","/data/data/com.android.email/databases/EmailProviderBackup.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/EmailProviderBody.db","/data/data/com.android.email/databases/EmailProviderBody.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/grants.db","/data/data/com.android.keychain/databases/grants.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/launcher.db","/data/data/com.android.launcher/databases/launcher.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/calendar.db","/data/data/com.android.providers.calendar/databases/calendar.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/contacts2.db","/data/data/com.android.providers.contacts/databases/contacts2.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/profile.db","/data/data/com.android.providers.contacts/databases/profile.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/downloads.db","/data/data/com.android.providers.downloads/databases/downloads.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/external.db","/data/data/com.android.providers.media/databases/external.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/internal.db","/data/data/com.android.providers.media/databases/internal.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/settings.db","/data/data/com.android.providers.settings/databases/settings.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/mmssms.db","/data/data/com.android.providers.telephony/databases/mmssms.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/telephony.db","/data/data/com.android.providers.telephony/databases/telephony.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/user_dict.db","/data/data/com.android.providers.userdictionary/databases/user_dict.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/qsb-log.db","/data/data/com.android.quicksearchbox/databases/qsb-log.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/webview.db","/data/media/0/webview.db");

				runLinuxRestoreCommand("/sdcard/perfectButterBackup/locksettings.db","/data/system/locksettings.db");
				runLinuxRestoreCommand("/sdcard/perfectButterBackup/accounts.db","/data/system/users/0/accounts.db");
			}
		});
		return v;
	}
	
	public void runLinuxRestoreCommand(String sourceFileName, String destinationFileName)
	{
		//todo find destination
		//to do : find source
		try{
			  Runtime.getRuntime().exec(new String[]{"/system/xbin/su","-c", "cp", sourceFileName, destinationFileName});
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}