package com.hackuva.menuclient;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MenuItemActivity extends ActionBarActivity {

	private ListView foodInfoListView;
	private TextView foodName;
	private Button presentButton;
	private Button absentButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item);
		
		String itemName = this.getIntent().getExtras().getString("itemName");
		String item [] = this.getIntent().getExtras().getStringArray("itemNutrition");
		this.foodInfoListView = (ListView)this.findViewById(R.id.item_info_list);
		this.foodInfoListView.setAdapter(new NutritionAdapter(this, android.R.layout.simple_list_item_1, item));
		this.foodName = (TextView)this.findViewById(R.id.item_info_name);
		this.foodName.setText(itemName);
		this.presentButton = (Button)this.findViewById(R.id.present_button);
		this.absentButton = (Button)this.findViewById(R.id.absence_button);
		
		this.presentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MenuItemActivity.this, "Food is definitely here", Toast.LENGTH_SHORT).show();
			}
		});
		this.absentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MenuItemActivity.this, "Food might not be here", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}

}
