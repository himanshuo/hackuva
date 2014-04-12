package com.hackuva.menuclient;

import java.util.Arrays;

/**
 * Class representing dining hall. Equivalent to menu hierarchy on backend
 * @author connor
 *
 */
public class DiningHall
{
	private String name;
	private Meal[] meals;
	
	
	public Meal[] getMeals()
	{
		return meals;
	}

	public void setMeals(Meal[] meals)
	{
		this.meals = meals;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return "[" + name + "," + Arrays.toString(meals) + "]";
	}

	public static class Meal
	{
		private String name;
		private Station[] stations;
		
		public Station[] getStations()
		{
			return stations;
		}
		
		public void setStations(Station[] stations)
		{
			this.stations = stations;
		}
		
		public String getName()
		{
			return name;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return "[" + name + "," + Arrays.toString(stations) + "]";
		}

	}
	
	public static class Station
	{
		private String name;
		private Item[] items;
		
		public String getName()
		{
			return name;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public Item[] getItems()
		{
			return items;
		}
		
		public void setItems(Item[] items)
		{
			this.items = items;
		}
		
		public String toString()
		{
			return "[" + name + "," + Arrays.toString(items) + "]";
		}
	}
	
	public static class Item
	{
		private String name;
		private String[] nutrition;
		
		public String getName()
		{
			return name;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public String[] getNutrition()
		{
			return nutrition;
		}
		
		public void setNutrition(String[] nutrition)
		{
			this.nutrition = nutrition;
		}
		
		public String toString()
		{
			return "[" + name + "," + Arrays.toString(nutrition) + "]";
		}
	}
}
