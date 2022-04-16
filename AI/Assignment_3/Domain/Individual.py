from copy import deepcopy
from random import *

from Domain.Gene import Gene
from Domain.Map import Map


directions = [(0,1), (0, -1), (1, 0), (-1, 0)]

class Individual:
    def __init__(self, size, battery = 0, map : Map = None):
        self.map = map
        self.size = size
        self.battery = battery
        self.chromosome = [Gene().gene for i in range(self.size)]
        self.fitness = None


    def findFitness(self):
        blocks = []     # visited blocks

        # generating a random initial position for drone that is also valid
        x, y = randrange(self.map.n), randrange(self.map.m)
        while self.map.checkBlock(x, y) is False:
            x, y = randrange(self.map.n), randrange(self.map.m)
        self.map.x, self.map.y = x, y
        moves = 0

        for gene in self.chromosome:
            if (x, y) not in blocks:
                blocks.append((x, y))
            positionCopy = (x, y)
            if 0 <= positionCopy[0] + gene[0] < self.map.n and 0 <= positionCopy[1] + gene[1] < self.map.m and self.map.surface[positionCopy[0] + gene[0]][positionCopy[1] + gene[1]] != -1:
                for dir in directions:
                    while(0 <= x + dir[0] < self.map.n and 0 <= y + dir[1] < self.map.m and self.map.surface[x + dir[0]][y + dir[1]] != 1):
                        x = x + dir[0]
                        y = y + dir[1]
                        if (x, y) not in blocks:
                            blocks.append((x, y))
                nextX, nextY = positionCopy[0] + gene[0],  positionCopy[1] + gene[1]
                if 0 <= nextX < self.map.n and 0 <= nextY < self.map.m and self.map.surface[nextX][nextY] != 1 and moves <= self.battery:
                    x, y = nextX, nextY
                    moves += 1

        # the fitness value is the number of blocks discovered by the sensors of the drone in the path
        self.fitness = len(blocks)


    def mutate(self, mutateProbability=0.04):
        if random() < mutateProbability:
            gene_idx = randrange(self.size)
            self.chromosome[gene_idx] = Gene().gene


    def crossover(self, otherParent, crossoverProbability=0.8):
        offspring1, offspring2 = Individual(self.size, self.battery, self.map), Individual(self.size, self.battery, self.map)
        if random() < crossoverProbability:
            split_point = randrange(1, self.size)
            # first child
            part1_os1 = self.chromosome[:split_point]
            part2_os1 = otherParent.chromosome[split_point:]
            offspring1.chromosome = part1_os1 + part2_os1
            # second child
            part1_os2 = otherParent.chromosome[:split_point]
            part2_os2 = self.chromosome[split_point:]
            offspring2.chromosome = part1_os2 + part2_os2

        return offspring1, offspring2


    def path(self):
        path = []
        block = [self.map.x, self.map.y]
        path.append(block)
        for gene in self.chromosome:
            [nextX, nextY] = path[-1]
            if 0 <= nextX + gene[0] < self.map.n and 0 <= nextY + gene[1] < self.map.m and self.map.surface[nextX + gene[0]][nextY + gene[1]] != 1:
                nextX += gene[0]
                nextY += gene[1]
                path.append([nextX, nextY])
        return path