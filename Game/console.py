from validators import Validator as v
from game import OverwritingError


class Console:
    def __init__(self, game):
        self._game = game

    def print_board(self):
        board = self._game.get_game_board()
        no_rows = len(board)
        for r in range(no_rows):
            print(board[r])

    def run_game(self):
        self.print_board()
        turn = 0
        stop = False
        while not stop:
            if turn == 1:   # it's chaos's turn
                command = input("Enter command ('row column symbol'):").split()
                if v.validate_user_input(command):
                    try:
                        self._game.chaos_makes_move(int(command[0]) - 1, int(command[1]) - 1, command[2])
                    except OverwritingError as oe:
                        print(oe)
                    if self._game.does_chaos_win():
                        print("CHAOS WINS!")
                        print("GAME IS OVER!")
                        stop = True
                    self.print_board()
                    turn = 0
                    print('\n')
                else:
                    print("Invalid user command!")
            else:       # it's order's turn
                self._game.order_makes_move()
                if self._game.does_order_win():
                    print("ORDER WINS!")
                    print("GAME IS OVER!")
                    stop = True
                self.print_board()
                turn = 1
                print('\n')