

class Validator:
    @staticmethod
    def is_integer(input):
        try:
            int(input)
            return True
        except ValueError:
            return False

    @staticmethod
    def validate_user_input(input):
        row = input[0]
        col = input[1]
        if Validator.is_integer(row) and Validator.is_integer(col):
            if 0 < int(row) <= 6 and 0 < int(col) <= 6:
                symbol = input[2]
                if symbol == 'x' or symbol == 'o':
                    return True
        return False
