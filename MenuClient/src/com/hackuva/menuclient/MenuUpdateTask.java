package com.hackuva.menuclient;

import android.os.AsyncTask;

/**
 * Update the menu in the background
 * @author connor
 *
 */
public class MenuUpdateTask extends AsyncTask<Boolean, DiningHall, Boolean>
{
	private final MainActivity callback;
	private boolean failFlag;
	
	public MenuUpdateTask(MainActivity callback)
	{
		this.callback = callback;
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
