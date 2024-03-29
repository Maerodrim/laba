package functions.factory;

import functions.*;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {

    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }

    @Override
    public TabulatedFunction create(double xFrom, double xTo, int count) {
        return new ArrayTabulatedFunction(xFrom, xTo, count);
    }

    @Override
    public TabulatedFunction create(Function func, double xFrom, double xTo, int count) {
        return  TabulatedFunctions.tabulate(func, xFrom, xTo, count);
    }

    @Override
    public TabulatedFunction create(FunctionPoint[] point) {
        return new ArrayTabulatedFunction(point);
    }

}