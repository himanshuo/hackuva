#Beautiful soup testing

from bs4 import BeautifulSoup
from urllib.request import urlopen
import sys

def get_first_child(tag):
	return next(tag.children)
	
def move_down(tag, level):
	for x in range(level):
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
	nut_sections = table.find_all('div', class_="section", recursive=False)
	return []
	
def get_station_menus(table):
	#Tables containing menu items at each station, indices correspond with station
	station_tables = table.find_all('div', recursive=False)
	station_menus = []
	for table in station_tables:
		items = get_content_list(table)
		nutritional_info = get_station_nutrition(table)
		#Zip into a single list
		items = zip(items, nutritional_info)
		station_menus.append(items)
	return station_menus

def build_menu(table):
	stations = get_content_list(table)
	station_menus = get_station_menus(table)
	print(stations)
	print(station_menus)
	
	
	
url = "http://www.campusdish.com/en-US/CSMA/Virginia/Home.htm?LocationID=138"#sys.argv[1]

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

print(build_menu(breakfast))
