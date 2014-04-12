#The classes to make this easier to read

import json

class Item:
	def __init__(self, name, nutrition=[]):
		self.name = name
		self.nutrition = nutrition
	def __repr__(self):
		return "[" + self.name + "," + str(self.nutrition) + "]"

class Station:
	def __init__(self, name, items=[]):
		self.name = name
		self.items = items
	def __repr__(self):
		return "[" + self.name + "," + str(self.items) + "]"

class Meal:
	def __init__(self, name, stations=[]):
		self.name = name
		self.stations = stations
	def __repr__(self):
		return "[" + self.name + "," + str(self.stations) + "]"
		
class DiningHall:
	def __init__(self, name, meals=[]):
		self.name = name
		self.meals = meals
	def __repr__(self):
		return "[" + self.name + "," + str(self.meals) + "]"


def get_json(objs):
	return json.dumps(objs, default=lambda o: o.__dict__)
