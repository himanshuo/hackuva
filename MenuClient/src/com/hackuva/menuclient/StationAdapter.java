package com.hackuva.menuclient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
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
		if (mView == null)
			mView = View.inflate(getContext(), R.layout.station_view, null);
		
		TextView text = (TextView)mView.findViewById(R.id.stationName);
		
		text.setText(station.getName());
		
		return mView;
	}
	
	public View inflateItemView(DiningHall.Item item, View mView)
	{
		if (mView == null)
			mView = View.inflate(getContext(), R.layout.item_view, null);
		
		TextView text = (TextView)mView.findViewById(R.id.itemName);
		
		text.setText(item.getName());
		
		return mView;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		View mView = convertView;
		
		Object obj = this.getItem(pos);
		
		if (obj instanceof DiningHall.Station)
			return inflateStationView((DiningHall.Station)obj, mView);
		else
			return inflateItemView((DiningHall.Item)obj, mView);
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
