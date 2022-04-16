import pickle
from random import *
import numpy as np


class Map:
    def __init__(self, n=20, m=20, x=None, y=None):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))
        if x is None:
            x = randrange(n)
        self.x = x
        if y is None:
            y = randrange(m)
        self.y = y

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = 1

    def freeBlocks(self):
        blocks = []
        for i in range(self.n):
            for j in range(self.m):
                if self.surface[i][j] == 0:
                    blocks.append((i, j))
        return blocks

    def checkBlock(self, x, y):
        if (x, y) in self.freeBlocks():
            return True
        return False

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string