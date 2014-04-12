package com.hackuva.menuclient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NutritionAdapter extends ArrayAdapter<String>
{

	public NutritionAdapter(Context context, int resource, String[] objects)
	{
		super(context, resource, objects);
	}
	
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		View mView = convertView;
		
		if (mView == null)
			mView = View.inflate(this.getContext(), R.layout.nut_text, null);
		
		TextView type = (TextView)mView.findViewById(R.id.type);
		TextView val = (TextView)mView.findViewById(R.id.value);
		
		String str = this.getItem(pos);
		
		type.setText(str.substring(0, str.indexOf(":") + 1));
		val.setText(str.substring(str.indexOf(":") + 1).trim());
		
		return mView;
	}

}
