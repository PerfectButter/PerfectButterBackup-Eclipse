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
	public static void exec(String cmd) {
		
		try{
			Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c",cmd});
  		}
  		catch (IOException e) {} 
  		
  		try{
  			  Runtime.getRuntime().exec(new String[]{"/system/xbin/su","-c",cmd});
  		}
  		catch (IOException e) {}
	
  		try{
  			  Runtime.getRuntime().exec(new String[]{"su","-c",cmd});
  		}
  		catch (IOException e) {}

//		try {
//		  Process suProcess = Runtime.getRuntime().exec("su");
//		  DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
//		  os.writeBytes(cmd + "\n");
//		  os.flush();
//		  os.writeBytes("exit\n");
//		  os.flush();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
