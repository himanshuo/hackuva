package com.hackuva.menuclient;

import java.util.Arrays;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity
{
	private DiningHall[] diningHalls;
	private Spinner diningLoc, meal;
	private ListView menuList;
	private int curDiningHall;		//Currently selected dining hall, needed for a few things
	private int curMeal;
	
	
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
		startMenuUpdate();	

		buildViews();
	}

	public void buildViews()
	{
		//Get the views from XML
		this.diningLoc = (Spinner)this.findViewById(R.id.dining_location);
		this.meal = (Spinner)this.findViewById(R.id.meal);
		this.menuList = (ListView)this.findViewById(R.id.big_list);
		
		//Now set the listeners. Note that the individual items in the listview listen for clicks, NOT THE LISTVIEW. Hence, it has no listener
		this.diningLoc.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				MainActivity.this.curDiningHall = position;
				refreshMeals();
				refreshMenuList();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				//Do nothing
			}
			
		});
		
		this.meal.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				MainActivity.this.curMeal = position;
				refreshMenuList();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				//Do nothing
			}
			
		});
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
		ProgressDialog dial = ProgressDialog.show(this, "Loading menu options", "Please wait...", true, false);
		MenuUpdateTask task = new MenuUpdateTask(this, dial);
		task.execute();
	}
	
	/**
	 * Get the list of names for active dining halls
	 * @return
	 */
	public String[] getDiningLocs()
	{
		String[] names = new String[this.diningHalls.length];
		
		for (int i = 0; i < this.diningHalls.length; i++)
			names[i] = diningHalls[i].getName();
		
		return names;
					
	}
	
	/**
	 * Get the possible meals for the current dining hall
	 * @return
	 */
	public String[] getPosMeals()
	{
		//Handle the case where no dining halls have been found. Think winter break.
		if (this.curDiningHall >= this.diningHalls.length)
			return new String[]{};
		
		DiningHall curHall = this.diningHalls[this.curDiningHall];
		
		String[] meals = new String[curHall.getMeals().length];
		
		for (int i = 0; i < curHall.getMeals().length; i++)
			meals[i] = curHall.getMeals()[i].getName();
		
		return meals;
	}
	
	/**
	 * Get the stations associated with the current dining hall
	 * @return
	 */
	public DiningHall.Station[] getStations()
	{
		//Handle the case where no dining halls have been found. Think winter break.
		if (this.curDiningHall >= this.diningHalls.length || this.curMeal >= this.diningHalls[this.curDiningHall].getMeals().length)
			return new DiningHall.Station[]{};
		
		return this.diningHalls[this.curDiningHall].getMeals()[this.curMeal].getStations();
	}
	
	public void refreshAllViews()
	{
		refreshDiningLocations();
		refreshMeals();
		refreshMenuList();
	}
	
	public void refreshDiningLocations()
	{
		if (diningHalls == null)
			return;
		
		//Only let this be changed here
		this.diningLoc.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.getDiningLocs()));
	}
	
	public void refreshMeals()
	{
		if (diningHalls == null)
			return;
		
		this.meal.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.getPosMeals()));
	}
	
	/**
	 * Build the views
	 */
	public void refreshMenuList()
	{
		if (diningHalls == null)
			return;

		//Set adapter
		this.menuList.setAdapter(new StationAdapter(this, android.R.layout.simple_list_item_1, this.getStations()));
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
		
		
		refreshAllViews();
	}
}
