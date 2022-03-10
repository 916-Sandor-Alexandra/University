from board import Board
from game import Game
from console import Console

if __name__ == '__main__':
    board = Board()
    game = Game(board)
    console = Console(game)
    console.run_game()