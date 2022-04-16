# import the pygame module, so you can use it
import time

import pygame
from queue import PriorityQueue

#Creating some colors
BLUE  = (0, 0, 255)
GRAYBLUE = (50,120,120)
RED   = (255, 0, 0)
GREEN = (0, 255, 0)
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

from task1.Domain.Drone import Drone
from task1.Domain.Map import Map

#define indexes variations
v = [[-1, 0], [1, 0], [0, 1], [0, -1]]
adj = [[-1,-1], [-1,0], [-1,1], [0,1], [1,1], [1,0], [1,-1], [0,-1], [-1,-1]]

class Controller():
    def __init__(self, map : Map, drone: Drone):
        self.map = map
        self.drone = drone

    def searchAStar(self, initialX, initialY, finalX, finalY):
        start_time = time.time()
        # setting up the start/end nodes
        self.drone.x = initialX
        self.drone.y = initialY
        start_node = self.drone
        start_node.g = start_node.h = start_node.f = 0
        end_node = Drone(None, finalX, finalY)
        end_node.g = end_node.h = end_node.f = 0

        open_list = []
        closed_list = []
        open_list.append(start_node)
        # until we reach the end
        while len(open_list) > 0:
            current_node = open_list[0]
            current_index = 0
            for index, item in enumerate(open_list):
                if item.f < current_node.f:
                    current_node = item
                    current_index = index
            open_list.pop(current_index)
            closed_list.append(current_node)

            # reached the goal, retrieving the path
            if current_node == end_node:
                path = []
                current = current_node
                while current is not None:
                    path.append((current.x, current.y))
                    current = current.parent
                return path[::-1]

            # generating adjacent nodes
            children = []
            for new_pos in v:
                node_position = (current_node.x + new_pos[0], current_node.y + new_pos[1])
                # check is positions are valid
                if node_position[0] > (self.map.n - 1) or node_position[0] < 0 or node_position[1] > (self.map.m - 1) or \
                        node_position[1] < 0:
                    continue
                # check if slot is available for drone to "walk" on
                if self.map.surface[node_position[0]][node_position[1]] != 0:
                    continue
                # appending the node to list
                new_node = Drone(current_node, node_position[0], node_position[1])
                children.append(new_node)

            for child in children:
                # child is on the closed list
                for closed_child in closed_list:
                    if child == closed_child:
                        continue
                # computing the g, h, f values
                child.g = current_node.g + 1
                child.h = ((child.x - end_node.x) ** 2) + ((child.y - end_node.y) ** 2)
                # child.h = abs(child.x - end_node.x) + abs(child.y - end_node.y)
                child.f = child.g + child.h

                # child is already in the open list
                for open_node in open_list:
                    if child == open_node and child.g > open_node.g:
                        continue
                open_list.append(child)
        print("--- A* search: %s seconds ---" % (time.time() - start_time))

    def heuristic(self, startX, startY, goalX, goalY):
        return abs(startX - goalX) + abs(startY - goalY)
        # return ((startX - goalX) ** 2) + ((startY - goalY) ** 2)


    def searchGreedy(self, initialX, initialY, finalX, finalY):
        start_time = time.time()
        pq = PriorityQueue()
        visited = []
        pq.put((self.heuristic(initialX, initialY, finalX, finalY), initialX, initialY))
        found = False

        while not pq.empty() and not found:
            (hv, x, y) = pq.get()
            visited.append((x, y))
            if (x, y) == (finalX, finalY):
                found = True

            for new_pos in v:
                node_position = (x + new_pos[0], y + new_pos[1])
                if node_position not in visited and 0 <= node_position[0] <= 19 and 0 <= node_position[1] <= 19 and \
                    self.map.surface[node_position[0]][node_position[1]] == 0:
                    pq.put((self.heuristic(node_position[0], node_position[1], finalX, finalY), node_position[0], node_position[1]))

        print("--- Greedy search: %s seconds ---" % (time.time() - start_time))
        return visited if found else None



    def dummysearch(self):
        # example of some path in test1.map from [5,7] to [7,11]
        return [[5, 7], [5, 8], [5, 9], [5, 10], [5, 11], [6, 11], [7, 11]]

    def displayWithPath(self, image, path):
        mark = pygame.Surface((20, 20))
        mark.fill(GREEN)
        for move in path:
            image.blit(mark, (move[1] * 20, move[0] * 20))

        return image