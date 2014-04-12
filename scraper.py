#Dining Menu Web Scraper

from bs4 import BeautifulSoup
from urllib.request import urlopen
import sys
import pymysql


def get_first_child(tag):
	return next(tag.children)
	
def move_down(tag, level):
	for x in range(level):
		if tag == None:
			return None
		tag = get_first_child(tag)
	return tag

def get_page(url):
	req = urlopen(url)
	return req.read()

def get_content_list(table):
	#Find the station names, stored in an <a> tag at the table's top level
	items = table.find_all('a', recursive=False)
	item_list = []
	for item in items:
		item_str = str(item.string).strip()
		item_str = item_str[:len(item_str) - (2 if item_str[len(item_str) - 1] == ">" else 0)].strip()
		item_list.append(item_str)
	return item_list

def get_station_nutrition(table):
	nut_sections = table.find_all('div', {"class" : "section"}, recursive=False)
	for x in range(len(nut_sections)):
		nut_sections[x] = move_down(nut_sections[x], 2)
	#Now get the nutritional information for each item:
	nut_list = []
	for item_nuts in nut_sections:
		my_nuts = []
		#The actual nutritional information. Pull info from "item" attrs
		for nut_tag in item_nuts.children:
			if nut_tag.has_attr('n') and nut_tag.has_attr('v'):
				name = str(nut_tag['n']).strip()
				value = str(nut_tag['v']).strip().replace('Â\xa0', ' ')
				#Make sure relevant attrs have valid values
				if name != "" and value != "" and value.find("99999.99") == -1:
					my_nuts.append(name + ": " + value)
		nut_list.append(my_nuts)
	return nut_list
	
def get_station_menus(table):
	#Tables containing menu items at each station, indices correspond with station
	station_tables = table.find_all('div', recursive=False)
	station_menus = []
	for table in station_tables:
		items = get_content_list(table)
		nutritional_info = get_station_nutrition(table)
		#Zip into a single list
		items = list(zip(items, nutritional_info))
		station_menus.append(items)
	return station_menus

def build_menu(table):
	"""Menu format: [[station, [item, [nutrition]]]]"""
	stations = get_content_list(table)
	station_menus = get_station_menus(table)
	return list(zip(stations, station_menus))
	
def get_menu(name, id):
	url = "http://www.campusdish.com/en-US/CSMA/Virginia/Home.htm?LocationID=" + id
	html = get_page(url)
	
	soup = BeautifulSoup(html)

	lunch = move_down(soup.find("table", id="menu1"), 2)

	has_dinner = len(soup.find_all("img", id="DinnerICN")) != 0

	#These could be swapped based on which is present
	if has_dinner:
		dinner = move_down(soup.find("table", id="menu2"), 2)
		breakfast = move_down(soup.find("table", id="menu3"), 2)
	else:
		dinner = None
		breakfast = move_down(soup.find("table", id="menu2"), 2)

	full_menu = []
	full_menu.append(["Lunch", build_menu(lunch)])

	if dinner != None:
		full_menu.append(["Dinner", build_menu(dinner)])
	if breakfast != None:
		full_menu.append(["Breakfast", build_menu(breakfast)])

	return [name, full_menu]

def pull_options(home_page):
	soup = BeautifulSoup(get_page(home_page))
	select = soup.find("select", {"name" : "WucChalkboard1:findLocations"})
	children = select.find_all("option")
	options_list = []
	for bitch_please in children:
		id = bitch_please['value']
		hall_name = str(bitch_please.string)
		options_list.append([hall_name, id])
	return options_list

options = pull_options("http://www.campusdish.com/en-us/CSMA/VIRGINIA")

menu_list = []

for option in options:
	menu_list.append(get_menu(option[0], option[1]))

"""Data format:
	[['Dining location', [['Meal', [['Station', [['Menu Item', [Nutrition]]]]]]]]]
   Justification:
   	-At each level, menu_list is a list of tuples
	-menu_list[0] gives you the first tuple in the outer list, which represents the first dining location: ['Dining location', [['Meal', [['Station', [['Menu Item', [Nutrition]]]]]]]]
	-menu_list[0][0] will give you the first value in that tuple: 'Dining location'
	-menu_list[0][1] gives you the second value in that tuple: [['Meal', [['Station', [['Menu Item', [Nutrition]]]]]]]
	-menu_list[0][1][0] will give you the first tuple in that list, which is ['Meal', [['Station', [['Menu Item', [Nutrition]]]]]]
	-menu_list[0][1][0][0] gives you the first value in the first tuple of the second list: 'Meal'
	-menu_list[0][1][0][1] gives you the second value in the first tuple, which is the list of tuples associated with it: [['Station', [['Menu Item', [Nutrition]]]]]
	-menu_list[0][1][0][1][0] will give you the first station
	-and so on...
	-We'll convert this to an object hierarchy at our soonest convenience. I'll probably handle that tomorrow.
"""

#We'll need to actually do something with this soon
print(menu_list)

#Open database connection
db = pymysql.connect("localhost","serveman","uvahacks","uvahacks" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

# execute SQL query using execute() method.
cursor.execute("SELECT VERSION()")

# Fetch a single row using fetchone() method.
data = cursor.fetchone()

print ("Database version : %s " % data)

# disconnect from server
db.close()

