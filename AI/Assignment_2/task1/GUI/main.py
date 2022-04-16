# import the pygame module, so you can use it
import pygame,time
from pygame.locals import *
from random import random, randint
from task1.Domain.Map import Map
from task1.Domain.Drone import Drone
from task1.Controller.Controller import Controller

#Creating some colors
BLUE  = (0, 0, 255)
GRAYBLUE = (50,120,120)
RED   = (255, 0, 0)
GREEN = (0, 255, 0)
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

#define directions
UP = 0
DOWN = 2
LEFT = 1
RIGHT = 3

#define indexes variations 
v = [[-1, 0], [1, 0], [0, 1], [0, -1]]
adj = [[-1,-1], [-1,0], [-1,1], [0,1], [1,1], [1,0], [1,-1], [0,-1], [-1,-1]]

                  
# define a main function
def main():
    # we create the map
    m = Map() 
    #m.randomMap()
    #m.saveMap("test2.map")
    m.loadMap("resources/test1.map")
    
    
    # initialize the pygame module
    pygame.init()
    # load and set the logo
    logo = pygame.image.load("resources/logo32x32.png")
    pygame.display.set_icon(logo)
    pygame.display.set_caption("Path in simple environment")
        
    # we position the drone somewhere in the area
    x = randint(0, 19)
    y = randint(0, 19)
    
    #create drona
    d = Drone(None, x, y)

    # creating the controller
    controller = Controller(m, d)

    # create a surface on screen that has the size of 400 x 480
    screen = pygame.display.set_mode((400,400))
    screen.fill(WHITE)
    
    
    # define a variable to control the main loop
    running = True
    start_x = 3
    start_y = 7
    end_x = 19
    end_y = 19
    if (start_x, start_y) not in m.freeSlots():
        raise Exception("Invalid start position for drone!")
    if (end_x, end_y) not in m.freeSlots():
        raise Exception("Invalid end position for drone!")

    astar_path = controller.searchAStar(start_x, start_y, end_x, end_y)
    greedy_path = controller.searchGreedy(start_x, start_y, end_x, end_y)

    chosen_path = greedy_path
    # chosen_path = greedy_path
    # print(greedy_path)
    idx = 0
    # main loop
    while running and idx < len(chosen_path):
        # event handling, gets all event from the event queue
        for event in pygame.event.get():
            # only do something if the event is of type QUIT
            if event.type == pygame.QUIT:
                # change the value to False, to exit the main loop
                running = False

            # if event.type == KEYDOWN:
            #     d.move(m) #this call will be erased
        d.x = chosen_path[idx][0]
        d.y = chosen_path[idx][1]
        idx = idx + 1
        time.sleep(0.2)
        screen.blit(d.mapWithDrone(m.image()),(0,0))
        pygame.display.flip()

    # path = controller.dummysearch()
    screen.blit(controller.displayWithPath(m.image(), chosen_path),(0,0))
    pygame.display.flip()
    time.sleep(5)
    pygame.quit()
     
     
# run the main function only if this module is executed as the main script
# (if you import this as a module then nothing is executed)
if __name__=="__main__":
    # call the main function
    try:
        main()
    except Exception as ex:
        print(ex)