import pymysql
import dining_objs.py
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

def make_all_unactive():
        #update which foods are active
        sql = """UPDATE Items SET active=0"""
        commit(sql)

def add_food(name, stall, nutrition):

        #update which foods are active
        sql = "INSERT INTO Items (name, stall,nutrition) VALUES ('{0}',{1},'{2}')".format(name, stall, nutrition)
        print(sql)
        commit(sql)     
        
def get_all_active_foods():
    sql = "SELECT * FROM Items WHERE active=1"
    commit(sql)
    return cursor.fetchall()

def get_all_active_dininghalls():
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


def get_dininghalls():
    foods = get_all_active_foods()
    stalls = get_all_active_stalls()
    meals = get_all_active_meals()
    dininghalls = get_all_active_dininghalls()
    
    oitems = []
    ostalls=[]
    omeals = []
    odininghalls = []
    
#items are complete
    for f in range(len(foods)):
        tempi = Item()
        tempi.name = foods[f][1]
        tempi.nutrition = foods[f][4]
        oitems.append(tempi)
    
#dining halls missing meals
    for d in range(len(dininghalls)):
        tempd = DiningHall()
        tempd.name = dininghalls[d][1]
        odininghalls.append(tempd)
#meals missing stalls
    for m in range(len(meals)):
        tempm = Meal()
        tempm.name= foods[m][1]
        omeals.append(tempm)

#stalls missing items
    for s in range(len(stalls)):
        temps = Station()
        temps.name = stalls[s][1]

#match food's stalls with stall
    for f in range(len(foods)):
        for s in range(len(stalls)):
            if foods[f][0]==stalls[s][0]
    
            if f[2]==s[0]: #if a specific stall contains the specific food
                        temps.items.append(tempi)
                if s[2]==m[0]:
                    tempm.stations.append(temps)
                
                

   for f in foods:
       for s in stalls:
           if f[2]==s[0]:
               ostalls[]
            
    
    
            
    



make_all_unactive()
set_active("pizza")
#add_food("spaghetti",1,"yum")

print(get_all_active_foods()[0])


#close db
db.close()
