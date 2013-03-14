package com.app.perfectbutterbackup;

import java.io.IOException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// for any functionality that's needed for the backup tab
public class BackupTabFragment extends Fragment
{
	public static String destinationPath = "/sdcard/perfectButterBackup/";
	
	public BackupTabFragment() { } 	// every Fragment should have a blank constructor. Smashing Android UI page 265.
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		return inflater.inflate(R.layout.backuptabfragmentlayout, container, false);
	}
	
	public static void runBackup(String password)
	{
		BackupTabFragment.makeDirectoryInsdCard();
		runLinuxCopyCommand("/data/data/com.android.browser/app_appcache/ApplicationCache.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/app_databases/Databases.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/app_geolocation/CachedGeoposition.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/app_icons/WebpageIcons.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/databases/autofill.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/databases/browser2.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/databases/webviewCookiesChromium.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.browser/databases/webviewCookiesChromiumPrivate.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.deskclock/databases/alarms.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProvider.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProviderBackup.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.email/databases/EmailProviderBody.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.keychain/databases/grants.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.launcher/databases/launcher.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.calendar/databases/calendar.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.contacts/databases/contacts2.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.contacts/databases/profile.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.downloads/databases/downloads.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.media/databases/external.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.media/databases/internal.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.settings/databases/settings.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.telephony/databases/mmssms.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.telephony/databases/telephony.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.providers.userdictionary/databases/user_dict.db", destinationPath);
		runLinuxCopyCommand("/data/data/com.android.quicksearchbox/databases/qsb-log.db", destinationPath);
		runLinuxCopyCommand("/data/system/locksettings.db", destinationPath);
		runLinuxCopyCommand("/data/system/users/0/accounts.db", destinationPath);
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