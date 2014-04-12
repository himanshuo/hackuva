package com.hackuva.menuclient;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{
	private DiningHall[] diningHalls;
	
	
	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Just what it says
		if (diningHalls == null)
			startMenuUpdate();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startMenuUpdate()
	{
		MenuUpdateTask task = new MenuUpdateTask(this);
		task.execute();
	}

	/**
	 * Notify the user of a failed update
	 */
	public void notifyFailedUpdate()
	{
		GenericAlertNotification note = new GenericAlertNotification();
		Bundle args = new Bundle();
		args.putString("title", "Update Failed");
		args.putString("message", "Could not reach server. Please do not panic, we're fixing the problem now.");
		note.setArguments(args);
		note.show(getSupportFragmentManager(), "failureNotification");
	}
	
	/**
	 * Update the dining list
	 * @param diningHalls
	 */
	public void updateDiningHalls(DiningHall[] diningHalls)
	{
		this.diningHalls = diningHalls;
		
		Log.d("Debug", Arrays.toString(diningHalls));
		
		refreshHallList();
	}
	
	public void refreshHallList()
	{
		//TODO
	}
}
