package com.hackuva.menuclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

import com.google.gson.Gson;


public class NetworkManager
{
	public static final String SERVER = "http://68.57.151.113:8000/";
	
	private NetworkManager()
	{
		//Static "library class". Do not allow instantiation.
	}
	
	/**
	 * Make get request to given URL
	 * @param url
	 * @return
	 */
	public static String doGet(String strUrl) throws IOException
	{
		URL url = new URL(strUrl);
		
		BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
		
		StringBuilder build = new StringBuilder();
		char[] buffer = new char[8196];
		int count;
		
		while ((count = read.read(buffer)) != -1)
			build.append(buffer, 0, count);
		
		return build.toString();
	}
	
	/**
	 * Get the dining hall list from the server
	 * @return
	 */
	public static DiningHall[] getDiningHalls()
	{
		try
		{
			String resp = NetworkManager.doGet(NetworkManager.SERVER + "?halls=all");
			Gson gson = new Gson();
			return gson.fromJson(resp, DiningHall[].class);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
