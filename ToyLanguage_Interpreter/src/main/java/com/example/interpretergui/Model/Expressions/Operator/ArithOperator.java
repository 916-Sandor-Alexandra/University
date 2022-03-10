package com.example.interpretergui.Model.Expressions.Operator;

import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;
import com.example.interpretergui.Model.Values.*;

public enum ArithOperator implements Operator<IntValue, IntValue> {
    PLUS {
        @Override
        public IntValue calc(IntValue x, IntValue y) {
            return new IntValue(x.getValue() + y.getValue());
        }

        @Override
        public String toString() {
            return "+";
        }
    },
    MINUS {
        @Override
        public IntValue calc(IntValue x, IntValue y) {
            return new IntValue(x.getValue() - y.getValue());
        }

        @Override
        public String toString() {
            return "-";
        }
    },
    MULTIPLY {
        @Override
        public IntValue calc(IntValue x, IntValue y) {
            return new IntValue(x.getValue() * y.getValue());
        }

        @Override
        public String toString() {
            return "*";
        }
    },
    DIVIDE {
        @Override
        public IntValue calc(IntValue x, IntValue y) throws DivisionByZeroException {
            if (y.getValue() == 0)
                throw new DivisionByZeroException("Cannot divide by zero!");

            return new IntValue(x.getValue() + y.getValue());
        }

        @Override
        public String toString() {
            return "/";
        }
    }
}
