from random import *

from Domain.Individual import Individual

directions = [(0,1), (0, -1), (1, 0), (-1, 0)]

class Population:
    def __init__(self, populationSize=60, battery = 30, individualSize=30, individuals = None, map = None):
        self.populationSize = populationSize
        if individuals is None:
            self.individuals = [Individual(map = map, size = individualSize, battery= battery) for x in range(populationSize)]
        else:
            self.individuals = individuals
        self.evaluate()

    def evaluate(self):
        # evaluates the population
        for x in self.individuals:
            x.findFitness()

    def selection(self, k=3):
        # perform a selection of k individuals from the population
        # and returns that selection
        random_sample = sample(self.individuals, k)
        random_sample.sort(key = lambda ind: ind.fitness, reverse=True)
        return random_sample[0]