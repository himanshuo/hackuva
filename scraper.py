#Beautiful soup testing

from bs4 import BeautifulSoup
from urllib.request import urlopen
import sys

def get_page(url):
	req = urlopen(url)
	return req.read()

def get_contents_list(table):
	#Find the station names, stored in an <a> tag at the table's top level
	items = table.find_all('a', recursive=False)
	item_list = []
	for item in items:
		item_str = str(item.string).strip()
		item_str = item_str[:len(item_str) - 2].strip()
		item_list.append(item_str)

	#Tables containing menu items at each station, indices correspond with station
	station_tables = table.find_all('div')

	print(station_tables)
	
	return item_list
	

def get_first_child(tag):
	return next(tag.children)
	
def move_down(tag, level):
	for x in range(level):
		tag = get_first_child(tag)
	return tag

	
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

print(get_contents_list(breakfast))
