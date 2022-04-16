# -*- coding: utf-8 -*-
# imports
import numpy
from matplotlib import pyplot

import Domain.Map
from Controller.controller import Controller
from Repository.repository import Repository
from UI.gui import *

noOfRuns = 150
populationSize = 20
individualSize = 20
battery = 30
mutationProbability = 0.05
crossoverProbability = 0.8

def menu():
    print("OPTION:\n1) Run the solver once\n2) Run solver 30 times and view the statistics\n3) View path of drone")


def run_solver(controller : Controller):
    start_time = time.time()
    fitnessAverage, bestSolution = controller.solver(noOfRuns, populationSize, battery, individualSize, mutationProbability, crossoverProbability)
    end_time = time.time()
    print("Execution Time for solver: ", end_time - start_time)
    print("The number of discovered blocks for the best solution is:", bestSolution.fitness)
    return fitnessAverage, bestSolution


def plot_statistics(fitnessAverage, noOfRuns):
    pyplot.plot(fitnessAverage)
    pyplot.xlim([1, noOfRuns])
    pyplot.xlabel("Iterations")
    pyplot.ylabel("Fitness")
    pyplot.savefig("statistics.png")
    pyplot.show()
    pyplot.close()


def main():
    m = Domain.Map.Map()
    # m.randomMap()
    repo = Repository(m)
    controller = Controller(repo)
    controller.repo.loadMap()
    controller.repo.saveMap()

    menu()
    executed = False
    bestSolution= None

    while True:
        option = int(input("Enter option:"))
        if option == 1:
            fitnessAverage, bestSolution = run_solver(controller)
            plot_statistics(fitnessAverage, noOfRuns)
            executed = True
        if option == 2:
            start_time = time.time()
            fitnessSolutions, _ = controller.multipleSolver(30, noOfRuns, populationSize, battery, individualSize, mutationProbability, crossoverProbability)
            end_time = time.time()
            average = numpy.average(fitnessSolutions)
            print("-> Execution time for solver: ", end_time - start_time)
            print("-> Fitness average: ", average)
            stdev = numpy.std(fitnessSolutions)
            print("-> Standard deviation: ", stdev)
        if option == 3:
            if executed == True:
                path = bestSolution.path()
                print(path)
                movingDrone(controller.repo.map, path)
            else:
                raise Exception("You should run the solver in order to view the path of the drone!")


if __name__=="__main__":
    # call the main function
    try:
        main()
    except Exception as ex:
      print(ex)
