package com.app.perfectbutterbackup;

import java.io.IOException;

public class BusyBox {


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
