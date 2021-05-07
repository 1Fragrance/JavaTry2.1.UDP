package com.company;

import Common.Model.ExpressionRequestModel;

public class ExpressionSolver {
    public double solveExpression(double x, double y, double z) {
        return Math.pow(y, x) + Math.sqrt((Math.abs(x) + Math.exp(y))) - ((Math.pow(z, 3) * Math.pow(Math.sin(y), 2))/(y + (Math.pow(z, 3) / (y - Math.pow(z, 3)))));
    }
}
