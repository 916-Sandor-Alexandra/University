package com.example.interpretergui.Model.Expressions.Operator;

import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.IntValue;

public enum RelationalOperator implements Operator<IntValue, BoolValue> {
    LESS {
        @Override
        public BoolValue calc(IntValue x, IntValue y)  {
            return new BoolValue(x.getValue() < y.getValue());
        }

        @Override
        public String toString() {
            return "<";
        }
    },
    LESS_OR_EQUAL {
        @Override
        public BoolValue calc(IntValue x, IntValue y)  {
            return new BoolValue(x.getValue() <= y.getValue());
        }

        @Override
        public String toString() {
            return "<=";
        }
    },
    EQUAL {
        @Override
        public BoolValue calc(IntValue x, IntValue y) {
            return new BoolValue(x.getValue() == y.getValue());
        }

        @Override
        public String toString() {
            return "==";
        }
    },
    DIFFERENT {
        @Override
        public BoolValue calc(IntValue x, IntValue y)  {
            return new BoolValue(x.getValue() != y.getValue());
        }

        @Override
        public String toString() {
            return "!=";
        }
    },
    GREATER {
        @Override
        public BoolValue calc(IntValue x, IntValue y)  {
            return new BoolValue(x.getValue() > y.getValue());
        }

        @Override
        public String toString() {
            return ">";
        }
    },
    GREATER_OR_EQUAL {
        @Override
        public BoolValue calc(IntValue x, IntValue y) {
            return new BoolValue(x.getValue() >= y.getValue());
        }

        @Override
        public String toString() {
            return ">=";
        }
    }
}
