package com.hackuva.menuclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Update the menu in the background
 * @author connor
 *
 */
public class MenuUpdateTask extends AsyncTask<Boolean, DiningHall, Boolean>
{
	private final MainActivity callback;
	private final ProgressDialog dial;
	private boolean failFlag;
	
	public MenuUpdateTask(MainActivity callback, ProgressDialog dial)
	{
		this.callback = callback;
		this.dial = dial;
		failFlag = false;
	}

	@Override
	protected Boolean doInBackground(Boolean... params)
	{
		DiningHall[] halls = NetworkManager.getDiningHalls();
		
		//Indicate that it could not get the dining halls, task failed.
		if (halls == null)
			failFlag = true;
		
		this.publishProgress(halls);
		
		return !failFlag;
	}
	
	protected void onProgressUpdate(DiningHall... diningHalls)
	{
		//Dismiss the loading dialog
		dial.dismiss();
		
		if (failFlag)
		{
			callback.notifyFailedUpdate();
		}
		else
		{
			callback.updateDiningHalls(diningHalls);
		}
	}

}
