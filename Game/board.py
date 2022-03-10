class Board:
    def __init__(self):
        self._dim = 6
        self._board = self.create_board()

    @property
    def board(self):
        return self._board

    @property
    def dim(self):
        return self._dim

    def create_board(self):
        '''
        Firstly, the game board is created.
        :param dim: the size of the board (no. of rows/columns)
        :return:
        '''
        board = []
        for r in range(self._dim):
            board.append([' ', ' ', ' ', ' ', ' ', ' '])
        return board

    def get_row(self, row):
        if row < 0 or row >= self._dim:
            raise ValueError
        return self._board[row]

    def place_piece(self, row, column, symbol):
        '''
        Assigns the game piece to the box corresponding to the row number and column.
        If the row/column input is out of the range, it raises Value Error.
        :return:
        '''
        if not(0 <= row < self._dim and 0 <= column < self._dim):
            raise ValueError
        self._board[row][column] = symbol

    def get_square(self, row, column):
        if not(0 <= row < self._dim and 0 <= column < self._dim):
            raise ValueError
        return self._board[row][column]

    def is_board_full(self):
        for r in range(self._dim):
            for c in range(self._dim):
                if self._board[r][c] == ' ':
                    return False
        return True
