from computer_strategy import ComputerStrategy


class OverwritingError(Exception):
    pass


class Game:
    def __init__(self, board):
        self._board = board
        self._ai_strategy = ComputerStrategy(self._board.board)

    def get_game_board(self):
        return self._board.board

    def chaos_makes_move(self, row, column, symbol):
        if self._board.get_square(row, column) != ' ':
            raise OverwritingError("This square is already filled!")
        self._board.place_piece(row, column, symbol)

    def check_horizontal_win(self, symbol):
        for r in range(self._board.dim):
            for add in range(self._board.dim - 5 + 1):
                counter = 0
                for c in range(add, add + 5):
                    if self._board.get_square(r, c) == symbol:
                        counter += 1
                if counter == 5:
                    return True
        return False

    def check_vertical_win(self, symbol):
        for c in range(self._board.dim):
            for add in range(self._board.dim - 5 + 1):
                counter = 0
                for r in range(add, add + 5):
                    if self._board.get_square(r, c) == symbol:
                        counter += 1
                if counter == 5:
                    return True
        return False

    def check_main_diagonal_win(self, symbol):
        for add in range(self._board.dim - 5 + 1):
            counter = 0
            for r in range(add, add + 5):
                if self._board.get_square(r + add, r + add) == symbol:
                    counter += 1
            if counter == 5:
                return True

        counter = 0
        for col in range(5):
            if self._board.get_square(col + 1, col) == symbol:
                counter += 1
        if counter == 5:
            return True

        counter = 0
        for col in range(1, 6):
            if self._board.get_square(col - 1, col) == symbol:
                counter += 1
        if counter == 5:
            return True

    def check_antidiagonal_win(self, symbol):
        for add in range(self._board.dim - 5 + 1):
            counter = 0
            for c in range(5 - add, 2 - add, -1):
                if self._board.get_square(5 - c, c) == symbol:
                    counter += 1
            if counter == 5:
                return True

        counter = 0
        for c in range(1, self._board.dim):
            if self._board.get_square(5 - c + 1, c) == symbol:
                counter += 1
        if counter == 5:
            return True

        counter = 0
        for c in range(self._board.dim - 1):
            if self._board.get_square(5 - c - 1, c) == symbol:
                counter += 1
        if counter == 5:
            return True

    def order_makes_move(self):
        symbol = self._ai_strategy.most_frequent_symbol()
        if self._ai_strategy.attempt_to_win_horizontally(symbol) is not None:
            choice = self._ai_strategy.attempt_to_win_horizontally(symbol)
            self._board.place_piece(choice[0], choice[1], symbol)
            return True
        if self._ai_strategy.attempt_to_win_antidiagonal(symbol) is not None:
            choice = self._ai_strategy.attempt_to_win_antidiagonal(symbol)
            self._board.place_piece(choice[0], choice[1], symbol)
            return True
        if self._ai_strategy.attempt_to_win_vertically(symbol) is not None:
            choice = self._ai_strategy.attempt_to_win_vertically(symbol)
            self._board.place_piece(choice[0], choice[1], symbol)
            return True
        if self._ai_strategy.attempt_to_win_vertically(symbol) is not None:
            choice = self._ai_strategy.attempt_to_win_vertically(symbol)
            self._board.place_piece(choice[0], choice[1], symbol)
            return True
        # if order can't win, it makes an useful move
        pick = self._ai_strategy.find_best_square(symbol)
        self._board.place_piece(pick[0], pick[1], symbol)

    def does_order_win(self, symbol):
        if self.check_horizontal_win(symbol) or self.check_vertical_win(symbol) or self.check_antidiagonal_win(symbol) or self.check_main_diagonal_win(symbol):
            return True
        return False

    def does_chaos_win(self):
        if self._board.is_board_full():
            return True
