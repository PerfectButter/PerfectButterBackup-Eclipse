package com.app.perfectbutterbackup;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class BusyBox {

	public static void installBusyBoxIfNecessary(Context context) {
//		File file = new File("/sdcard/busybox-1.20.2r2");
//		if (/* !file.exists() */true) {
//			// busybox isn't available
//			// so, let's install it
//
//			try {
//				InputStream is = context.getAssets().open("busybox-1.20.2r2");
//				FileOutputStream os = new FileOutputStream(
//						"/sdcard/PerfectButter_busybox");
//
//				byte[] buffer = new byte[1024];
//				while (true) {
//					int count = is.read(buffer);
//					if (count <= 0)
//						break; // end of file
//					os.write(buffer, 0, count);
//				}
//				is.close();
//				os.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				try {
//					Toast.makeText(context, "Busybox installation failed", 1000)
//							.show();
//				} catch (Exception e2) {
//				}
//			}
//
//			// set permission of the file to linux executable
//			try {
//				Runtime.getRuntime()
//						.exec("/system/xbin/su -c cp /sdcard/PerfectButter_busybox /system/xbin/PerfectButter_busybox");
//				Runtime.getRuntime()
//						.exec("/system/xbin/su -c /system/bin/chmod 777 /system/xbin/PerfectButter_busybox");
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		// file.delete();
	}
	// to execute linux commands using busy box. 
	public static void exec(final String cmd) {
		exec(cmd, null);
	}
	public static void exec(final String cmd, final BusyBoxCallback callback) {
		
		Runnable r = new Runnable() {
			public void run() {
				exec_in_background_thread(cmd, callback);
			}
		};
		
		Thread t = new Thread(r);
		t.start();
	}
	public static void exec_in_background_thread(String cmd, BusyBoxCallback callback) {
		
		String su_locations[] = {
				"/system/bin/su",
				"/system/xbin/su",
				"su" };
		
		for(int i=0; i<su_locations.length; i++)
		{
			try{
				Process process = Runtime.getRuntime().exec(new String[]{su_locations[i],"-c",cmd});
				process.waitFor();
				
				// notify the caller using this callback
				if(callback!=null) callback.onExecCompleted();
				
				// exec ran successfully
				break;
	  		}
	  		catch (IOException e) {
	  			// keep trying other locations
	  		} 
			catch (InterruptedException e) {
				break;
			}
		}
	}
}




interface BusyBoxCallback
{
	void onExecCompleted();
}
