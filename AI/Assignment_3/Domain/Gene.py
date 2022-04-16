from random import *

dir = [(0, 1), (0, -1), (1, 0), (-1, 0)]

class Gene:
    def __init__(self):
        # direction
        self.gene = choice(dir)

