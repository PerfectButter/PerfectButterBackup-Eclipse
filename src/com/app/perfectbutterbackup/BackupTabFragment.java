package com.app.perfectbutterbackup;

import java.io.IOException;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

// for any functionality that's needed for the backup tab
public class BackupTabFragment extends Fragment
{
	// every Fragment should have a blank constructor. Smashing Android UI page 265.
	public BackupTabFragment()
	{
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
        //return inflater.inflate(R.layout.backuptabfragmentlayout, container, false);
        
		View v = inflater.inflate(R.layout.backuptabfragmentlayout, container, false);
		Button backupButton = (Button) v.findViewById(R.id.runBackupButton);
		backupButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//System.out.println("the backupbutton works");
				makeDirectoryInsdCard();
				//runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProvider.db");
				
				runLinuxCopyCommand("/data/data/com.android.browser/app_appcache/ApplicationCache.db");
				runLinuxCopyCommand("/data/data/com.android.browser/app_databases/Databases.db");
				runLinuxCopyCommand("/data/data/com.android.browser/app_geolocation/CachedGeoposition.db");
				runLinuxCopyCommand("/data/data/com.android.browser/app_icons/WebpageIcons.db");
				runLinuxCopyCommand("/data/data/com.android.browser/databases/autofill.db");
				runLinuxCopyCommand("/data/data/com.android.browser/databases/browser2.db");
				runLinuxCopyCommand("/data/data/com.android.browser/databases/webview.db");
				runLinuxCopyCommand("/data/data/com.android.browser/databases/webviewCookiesChromium.db");
				runLinuxCopyCommand("/data/data/com.android.browser/databases/webviewCookiesChromiumPrivate.db");
				runLinuxCopyCommand("/data/data/com.android.deskclock/databases/alarms.db");
				runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProvider.db");
				runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProviderBackup.db");
				runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProviderBody.db");
				runLinuxCopyCommand("/data/data/com.android.keychain/databases/grants.db");
				runLinuxCopyCommand("/data/data/com.android.launcher/databases/launcher.db");
				runLinuxCopyCommand("/data/data/com.android.providers.calendar/databases/calendar.db");
				runLinuxCopyCommand("/data/data/com.android.providers.contacts/databases/contacts2.db");
				runLinuxCopyCommand("/data/data/com.android.providers.contacts/databases/profile.db");
				runLinuxCopyCommand("/data/data/com.android.providers.downloads/databases/downloads.db");
				runLinuxCopyCommand("/data/data/com.android.providers.media/databases/external.db");
				runLinuxCopyCommand("/data/data/com.android.providers.media/databases/internal.db");
				runLinuxCopyCommand("/data/data/com.android.providers.settings/databases/settings.db");
				runLinuxCopyCommand("/data/data/com.android.providers.telephony/databases/mmssms.db");
				runLinuxCopyCommand("/data/data/com.android.providers.telephony/databases/telephony.db");
				runLinuxCopyCommand("/data/data/com.android.providers.userdictionary/databases/user_dict.db");
				runLinuxCopyCommand("/data/data/com.android.quicksearchbox/databases/qsb-log.db");
				runLinuxCopyCommand("/data/media/0/webview.db");
				runLinuxCopyCommand("/data/system/locksettings.db");
				runLinuxCopyCommand("/data/system/users/0/accounts.db");
				
				
			}
		});
		
		return v;
		
	}
	
	public void makeDirectoryInsdCard()
	{
		try{
			  Runtime.getRuntime().exec("/system/xbin/su -c mkdir /sdcard/perfectButterBackup");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runLinuxCopyCommand(String fileName)
	{
		try{
			  Runtime.getRuntime().exec(new String[]{"/system/xbin/su","-c", "cp", fileName, "/sdcard/perfectButterBackup/"});
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}