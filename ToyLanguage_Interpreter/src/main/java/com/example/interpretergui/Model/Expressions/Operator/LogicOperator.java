package com.example.interpretergui.Model.Expressions.Operator;

import com.example.interpretergui.Model.Values.BoolValue;

public enum LogicOperator implements Operator<BoolValue, BoolValue> {
    AND {
        @Override
        public BoolValue calc(BoolValue x, BoolValue y) {
            return new BoolValue(x.getValue() && y.getValue());
        }

        @Override
        public String toString() {
            return "and";
        }
    },
    OR {
        @Override
        public BoolValue calc(BoolValue x, BoolValue y) {
            return new BoolValue(x.getValue() || y.getValue());
        }

        @Override
        public String toString() {
            return "or";
        }
    }
}
