import random


class ComputerStrategy:
    def __init__(self, board):
        self._board = board

    def most_frequent_symbol(self):
        xs = 0
        os = 0
        for r in range(len(self._board)):
            for c in range(len(self._board)):
                if self._board[r][c] == 'x':
                    xs += 1
                if self._board[r][c] == 'o':
                    os += 1
        if xs >= os:
            return 'x'
        else:
            return 'o'

    def get_number_good_of_neighbors(self, row, col, symbol):
        number = 0
        dim = len(self._board) - 1
        if row > 0 and col < dim:
            dir = [0, 1, -1]
            for i in range(len(dir)):
                for j in range(len(dir)):
                    if dir[i] != 0 and dir[j] != 0:
                        if self._board[row + dir[i]][col + dir[j]] == symbol:
                            number += 1
            return number
        if row == 0:
            if col == 0:
                if self._board[row][col + 1] == symbol:
                    number += 1
                if self._board[row + 1][col] == symbol:
                    number += 1
                if self._board[row + 1][col + 1] == symbol:
                    number += 1
                return number
            elif col == dim:
                if self._board[row][col - 1] == symbol:
                    number += 1
                if self._board[row + 1][col] == symbol:
                    number += 1
                if self._board[row + 1][col - 1] == symbol:
                    number += 1
                return number
            else:
                if self._board[row][col + 1] == symbol:
                    number += 1
                if self._board[row + 1][col] == symbol:
                    number += 1
                if self._board[row + 1][col + 1] == symbol:
                    number += 1
                if self._board[row][col - 1] == symbol:
                    number += 1
                if self._board[row + 1][col - 1] == symbol:
                    number += 1
                return number
        if col == 0:
            if row == dim:
                if self._board[row][col + 1] == symbol:
                    number += 1
                if self._board[row - 1][col] == symbol:
                    number += 1
                if self._board[row - 1][col + 1] == symbol:
                    number += 1
                return number
            else:
                if self._board[row + 1][col] == symbol:
                    number += 1
                if self._board[row + 1][col + 1] == symbol:
                    number += 1
                if self._board[row][col + 1] == symbol:
                    number += 1
                if self._board[row - 1][col + 1] == symbol:
                    number += 1
                if self._board[row - 1][col] == symbol:
                    number += 1
                return number
        if row == dim:
            if col == dim:
                if self._board[row][col - 1] == symbol:
                    number += 1
                if self._board[row - 1][col] == symbol:
                    number += 1
                if self._board[row - 1][col - 1] == symbol:
                    number += 1
                return number
            else:
                if self._board[row][col + 1] == symbol:
                    number += 1
                if self._board[row - 1][col + 1] == symbol:
                    number += 1
                if self._board[row - 1][col] == symbol:
                    number += 1
                if self._board[row - 1][col - 1] == symbol:
                    number += 1
                if self._board[row][col - 1] == symbol:
                    number += 1
                return number

        if col == dim:
            if self._board[row + 1][col] == symbol:
                number += 1
            if self._board[row + 1][col - 1] == symbol:
                number += 1
            if self._board[row][col - 1] == symbol:
                number += 1
            if self._board[row - 1][col - 1] == symbol:
                number += 1
            if self._board[row - 1][col] == symbol:
                number += 1
            return number

    def find_best_square(self, symbol):
        best_spots = []
        maximum_number = 0
        for r in range(len(self._board)):
            for c in range(len(self._board)):
                if self._board[r][c] == symbol:
                    neighbors = self.get_number_good_of_neighbors(r, c, symbol)
                    if neighbors >= maximum_number:
                        if neighbors == maximum_number:
                            best_spots.append([r, c])
                        else:
                            best_spots.clear()
                            best_spots.append([r, c])
                            maximum_number = neighbors
        if len(best_spots) > 1:
            return best_spots[0]
        else:
            index_pick = random.randint(0, len(best_spots) - 1)
            return [best_spots[index_pick][0], best_spots[index_pick][1]]

    def attempt_to_win_vertically(self, symbol):
        dim = len(self._board)
        good_spot = 0
        for c in range(dim):
            for add in range(dim - 5 + 1):
                counter = 0
                found = False
                for r in range(add, add + 5):
                    if self._board[r][c] == symbol:
                        counter += 1
                    if self._board[r][c] == ' ':
                        found = True
                        good_spot = [r, c]
                if counter == 4 and found:
                    return good_spot

    def attempt_to_win_horizontally(self, symbol):
        dim = len(self._board)
        good_spot = 0
        for r in range(dim):
            for add in range(dim - 5 + 1):
                counter = 0
                found = False
                for c in range(add, add + 5):
                    if self._board[r][c] == symbol:
                        counter += 1
                    if self._board[r][c] == ' ':
                        found = True
                        good_spot = [r, c]
                if counter == 4 and found:
                    return good_spot

    def attempt_to_win_main_diagonal(self, symbol):
        dim = len(self._board)
        good_spot = 0
        for add in range(dim - 5 + 1):
            counter = 0
            found = False
            for r in range(add, add + 5):
                if self._board[r + add][r + add] == symbol:
                    counter += 1
                if self._board[r + add][r + add] == ' ':
                    found = True
                    good_spot = [r + add, r + add]
            if counter == 4 and found:
                return good_spot

        counter = 0
        found = False
        for col in range(5):
            if self._board[col + 1][col] == symbol:
                counter += 1
            if self._board[col + 1][col] == ' ':
                found = True
                good_spot = [col + 1, col]
        if counter == 4 and found:
            return good_spot

        counter = 0
        found = False
        for col in range(1, 6):
            if self._board[col - 1][col] == symbol:
                counter += 1
            if self._board[col - 1][col] == ' ':
                found = True
                good_spot = [col - 1, col]
        if counter == 4 and found:
            return good_spot

    def attempt_to_win_antidiagonal(self, symbol):
        dim = len(self._board)
        good_spot = 0
        for add in range(dim - 5 + 1):
            counter = 0
            found = False
            for c in range(5 - add, 2 - add, -1):
                if self._board[5 - c][c] == symbol:
                    counter += 1
                if self._board[5 - c][c] == ' ':
                    found = True
                    good_spot = [5 - c, c]
            if counter == 4 and found:
                return good_spot

        counter = 0
        found = False
        for c in range(1,dim):
            if self._board[5 - c + 1][c] == symbol:
                counter += 1
            if self._board[5 - c + 1][c] == ' ':
                found = True
                good_spot = [5 - c + 1, c]
        if counter == 4 and found:
            return good_spot

        counter = 0
        for c in range(dim - 1):
            if self._board[5 - c - 1][c] == symbol:
                counter += 1
            if self._board[5 - c - 1][c] == ' ':
                found = True
                good_spot = [5 - c - 1, c]
        if counter == 4 and found:
            return good_spot
