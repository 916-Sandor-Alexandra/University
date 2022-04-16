import pygame
from pygame.locals import *
import time


class Drone():
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.visited = []
        self.stack = []

    def move(self, detectedMap):
        pressed_keys = pygame.key.get_pressed()
        if self.x > 0:
            if pressed_keys[K_UP] and detectedMap.surface[self.x - 1][self.y] == 0:
                self.x = self.x - 1
        if self.x < 19:
            if pressed_keys[K_DOWN] and detectedMap.surface[self.x + 1][self.y] == 0:
                self.x = self.x + 1

        if self.y > 0:
            if pressed_keys[K_LEFT] and detectedMap.surface[self.x][self.y - 1] == 0:
                self.y = self.y - 1
        if self.y < 19:
            if pressed_keys[K_RIGHT] and detectedMap.surface[self.x][self.y + 1] == 0:
                self.y = self.y + 1
        else:
            return False

    def update(self, map, env, screen):
        map.markDetectedWalls(env, self.x, self.y)
        screen.blit(map.image(self.x, self.y), (400, 0))
        pygame.display.flip()

    def moveDFS(self, detectedMap):
        time.sleep(1)
        self.visited.append((self.x, self.y))
        # UP
        if (self.x > 0 and detectedMap.surface[self.x - 1][self.y] == 0 and (self.x - 1, self.y) not in self.visited):
            self.stack.append((self.x, self.y))
            self.x = self.x - 1
            self.visited.append((self.x, self.y))
        # RIGHT
        elif (self.y < 19 and detectedMap.surface[self.x][self.y + 1] == 0 and (
        self.x, self.y + 1) not in self.visited):
            self.stack.append((self.x, self.y))
            self.y = self.y + 1
            self.visited.append((self.x, self.y))
        # DOWN
        elif (self.x < 19 and detectedMap.surface[self.x + 1][self.y] == 0 and (
        self.x + 1, self.y) not in self.visited):
            self.stack.append((self.x, self.y))
            self.x = self.x + 1
            self.visited.append((self.x, self.y))
        # LEFT
        elif (self.y > 0 and detectedMap.surface[self.x][self.y - 1] == 0 and (self.x, self.y - 1) not in self.visited):
            self.stack.append((self.x, self.y))
            self.y = self.y - 1
            self.visited.append((self.x, self.y))
        elif len(self.stack) != 0:
            self.x, self.y = self.stack.pop()