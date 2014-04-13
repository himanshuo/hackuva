import pymysql
from dining_objs import *
#functions:
#set_active(*arg) parameters are names of foods to make active
#set_unactive(*arg) paramters are names of foods to make unactive
#add_food(full_name, categories_id, nutritional_info)

#Open database connection                                                    
db = pymysql.connect("localhost","serveman","uvahacks","uvahacks" )

# prepare a cursor object using cursor() method                             
cursor = db.cursor()


def commit(sql):
    try:
        # Execute the SQL command                                        
        cursor.execute(sql)
        #Commit your changes in the database                               
        db.commit()
    except:
        # Rollback in case there is any error                            
        db.rollback()
        print("failed")

#make a food active
def set_active(*arg):
    #update which foods are active
    sql = """UPDATE Items SET active=1 WHERE """
    for i in range(len(arg)):
        if(i==0):
            sql=sql+"name='"+arg[i]+"'"
        else:
            sql=sql+"OR name='"+arg[i]+"'"
    
    print(sql)
    commit(sql)
                         

def set_unactive(*arg):
        #update which foods are unactive
        sql = """UPDATE Items SET active=0 WHERE """
        for i in range(len(arg)):
            if(i==0):
                sql=sql+"name='"+arg[i]+"'"
            else:
                sql=sql+"OR name='"+arg[i]+"'"
        print(sql)
        commit(sql)

def clear_tables():
    commit("DELETE FROM Items")
    commit("DELETE FROM Stalls")
    commit("DELETE FROM Meals")
    commit("DELETE FROM DiningHalls")

def make_all_unactive():
        #update which foods are active
        sql = """UPDATE Items SET active=0"""
        commit(sql)
        sql = "UPDATE Stalls SET active=0"
        commit(sql)
        sql = "UPDATE Meals SET active=0"
        commit(sql)
        sql = "Update DiningHalls SET active=0"

def add_food(name, stall, nutrition):

        #update which foods are active
        sql = "INSERT INTO Items (name, stall,nutrition) VALUES ('{0}',{1},'{2}')".format(name, stall, nutrition)
        print(sql)
        commit(sql)     
        
def get_all_active_foods():
    sql = "SELECT * FROM Items WHERE active=1"
    commit(sql)
    return cursor.fetchall()

def get_all_active_dining_halls():
    sql = "SELECT * FROM DiningHalls WHERE active=1"
    commit(sql)
    return cursor.fetchall()
def get_all_active_meals():
    sql = "SELECT * FROM Meals WHERE active=1"
    commit(sql)
    return cursor.fetchall()
def get_all_active_stalls():
    sql = "SELECT * FROM Stalls WHERE active=1"
    commit(sql)
    return cursor.fetchall()


def get_dining_halls():
    foods = get_all_active_foods()
    stalls = get_all_active_stalls()
    meals = get_all_active_meals()
    dining_halls = get_all_active_dining_halls()
    
    print(foods)
    print(stalls)
    print(meals)
    print(dining_halls)

    oitems = []
    ostalls=[]
    omeals = []
    odininghalls = []
    
    #items are complete
    for f in range(len(foods)):
        tempi = Item(foods[f][1], [item.strip() for item in foods[f][4][1:len(foods[f][4])].split(',')])
        oitems.append(tempi)
    
    #dining halls missing meals
    for d in range(len(dining_halls)):
        tempd = DiningHall(dining_halls[d][1])
        odininghalls.append(tempd)

    #meals missing stalls
    for m in range(len(meals)):
        tempm = Meal(meals[m][1])
        omeals.append(tempm)

    #stalls missing items
    for s in range(len(stalls)):
        temps = Station(stalls[s][1])
        ostalls.append(temps)

    #match food's stalls with stall
    for f in range(len(foods)):
        for s in range(len(stalls)):
            if foods[f][2] == stalls[s][0]:
                ostalls[s].items.append(oitems[f])
                
    for s in range(len(stalls)):
        for m in range(len(meals)):
            if stalls[s][2] == meals[m][0]:
                omeals[m].stations.append(ostalls[s])

    for m in range(len(meals)):
        for d in range(len(dining_halls)):
            if meals[m][2] == dining_halls[d][0]:
                odininghalls[d].meals.append(omeals[m])
    
    return odininghalls
    
def get_last_insert_id():
    return commit("SELECT LAST_INSERT_ID()")[0]
    
def insert_dining_hall(dining_hall):
    commit("INSERT INTO DiningHalls(name, active) VALUES ({:s}, 1)".format(dining_hall.name))
    return get_last_insert_id()

def insert_meal(dining_hall_id, meal):
    commit("INSERT INTO Meals(name, dining_hall, active) VALUES ({:s}, {:s}, 1)".format(meal.name, dining_hall_id))
    return get_last_insert_id()

def insert_station(meal_id, station):
    commit("INSERT INTO Stations(name, meal, active) VALUES ({:s}, {:s}, 1)".format(station.name, meal_id))
    return get_last_insert_id()

def insert_item(station_id, item):
    commit("INSERT INTO Items(name, station, active) VALUES ({:s}, {:s}, 1, {:s})".format(item.name, station_id, item.nutrition))
    return get_last_insert_id()
      
def insert_all(dining_halls):
    clear_tables()
    for dining_hall in dining_halls:
        dining_hall_id = insert_dining_hall(dining_hall)
        for meal in dining_hall.meals:
            meal_id = insert_meal(dining_hall_id, meal)
            for station in meal.stations:
                station_id = insert_station(meal_id, station)
                for item in station.items:
                    insert_item(station_id, item)



#print(get_json(get_dining_halls()))


#close db
db.close()
