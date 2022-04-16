# -*- coding: utf-8 -*-

import pickle

from Domain.Map import Map
from Domain.Population import Population


class Repository():
    def __init__(self, map):
        self.populations = []
        self.map = map
        
    def createPopulation(self, populationSize, battery, individualSize):
        self.populations.append(Population(map = self.map, populationSize = populationSize, battery = battery, individualSize = individualSize))

    def addPopulation(self, population):
        self.populations.append(population)

    def loadMap(self):
        with open("../resources/myMap.map", "rb") as f:
            self.map = pickle.load(f)
            f.close()

    def saveMap(self):
        with open("../resources/myMap.map", "wb") as f:
            pickle.dump(self.map, f)
            f.close()
            