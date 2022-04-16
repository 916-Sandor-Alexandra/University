from copy import deepcopy
from random import seed, randint, randrange

import numpy

from Domain.Population import Population
from Repository.repository import Repository

directions = [(0,1), (0, -1), (1, 0), (-1, 0)]


class Controller():
    def __init__(self, repository : Repository):
        self.repo = repository
    

    def iteration(self, crossoverProbability, mutationProbability):
        lastPopulation = self.repo.populations[-1]
        i1 = randrange(len(lastPopulation.individuals))
        i2 = randrange(len(lastPopulation.individuals))
        newPopulation = deepcopy(lastPopulation)
        if i1 != i2:
            individual1 = newPopulation.individuals[i1]
            individual2 = newPopulation.individuals[i2]
            offspring1, offspring2 = individual1.crossover(individual2, crossoverProbability)
            offspring1.mutate(mutationProbability)
            offspring2.mutate(mutationProbability)
            offspring1.findFitness()
            offspring2.findFitness()
            if  offspring1.fitness > individual1.fitness and offspring1.fitness > offspring2.fitness:
                newPopulation.individuals[i1] = offspring1
            if  offspring2.fitness > individual1.fitness and offspring2.fitness > offspring1.fitness:
                newPopulation.individuals[i1] = offspring2
            if  offspring1.fitness > individual2.fitness and offspring1.fitness > offspring2.fitness:
                newPopulation.individuals[i2] = offspring1
            if  offspring2.fitness > individual2.fitness and offspring2.fitness > offspring1.fitness:
                newPopulation.individuals[i2] = offspring2
        self.repo.addPopulation(newPopulation)

        
    def run(self, noOfIterations, mutationProbability, crossoverProbability):
        fitnessAverage = []
        bestSolution = None
        for x in range(noOfIterations):
            self.iteration(crossoverProbability, mutationProbability)
            population = self.repo.populations[-1]
            fitnessList = []
            # computing the average fitness for the population and getting the best solution as well
            for individual in population.individuals:
                fitnessList.append(individual.fitness)
                if bestSolution == None or individual.fitness > bestSolution.fitness:
                    bestSolution = individual
            fitnessAverage.append(numpy.average(fitnessList))

        # returning the stats info and the best solution (Individual)
        return fitnessAverage, bestSolution
    
    def solver(self, noOfIterations, populationSize, battery, individualSize, mutationProbability, crossoverProbability):
        self.repo.createPopulation(populationSize, battery, individualSize)
        return self.run(noOfIterations, mutationProbability, crossoverProbability)

    def multipleSolver(self, noOfRuns, noOfIterations, populationSize, battery, individualSize, mutationProbability, crossoverProbability):
        fitnessSolutions = []
        bestSolutionOverall = None
        bestFitnessOverall = None
        for i in range(noOfRuns):
            seed(i)
            fitnessAverage, bestSolution = self.solver(noOfIterations, populationSize, battery, individualSize, mutationProbability, crossoverProbability)
            # print(bestSolution.fitness)
            fitnessSolutions.append(bestSolution.fitness)
            if bestFitnessOverall is None or bestSolution.fitness > bestFitnessOverall:
                bestFitnessOverall = bestSolution.fitness
                bestSolutionOverall = bestSolution
        return fitnessSolutions, bestSolutionOverall

       