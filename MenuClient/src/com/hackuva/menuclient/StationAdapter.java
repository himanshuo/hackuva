package com.hackuva.menuclient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StationAdapter extends ArrayAdapter<Object>
{
	public StationAdapter(Context context, int resource, DiningHall.Station[] stations)
	{
		super(context, resource, combineStationItems(stations));
	}
	
	public View inflateStationView(DiningHall.Station station, View mView)
	{
		mView = View.inflate(getContext(), R.layout.station_view, null);
		
		TextView text = (TextView)mView.findViewById(R.id.stationName);
		
		text.setText(station.getName());
		
		return mView;
	}
	
	public View inflateItemView(final DiningHall.Item item, View mView)
	{
		mView = View.inflate(getContext(), R.layout.item_view, null);
		
		TextView text = (TextView)mView.findViewById(R.id.itemName);
		
		String name = item.getName();
		
		text.setText(name);
		
		//Register a click listener
		mView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v)
			{
				Intent launcher = new Intent(StationAdapter.this.getContext(), MainActivity.class/*MenuItemActivity.class*/);
				launcher.putExtra("itemName", item.getName());
				launcher.putExtra("itemNutrition", item.getNutrition());
				StationAdapter.this.getContext().startActivity(launcher);
			}
			
		});
		
		return mView;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		View mView = convertView;
		
		Object obj = this.getItem(pos);
		
		if (obj instanceof DiningHall.Station)
			return inflateStationView((DiningHall.Station)obj, mView);
		else if (obj instanceof DiningHall.Item)
			return inflateItemView((DiningHall.Item)obj, mView);
		else
			return mView;
	}
	
	/**
	 * Put in single object list
	 * @param stations
	 * @return
	 */
	public static List<Object> combineStationItems(DiningHall.Station[] stations)
	{
		List<Object> objList = new ArrayList<Object>();
		
		for (DiningHall.Station station : stations)
		{
			objList.add(station);
			
			for (DiningHall.Item item : station.getItems())
			{
				objList.add(item);
			}
		}
		
		return objList;
	}

}
