package functions.factory;

import functions.*;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    @Override
    public TabulatedFunction create(double xFrom, double xTo, int count) {
        return new LinkedListTabulatedFunction( xFrom, xTo, count);
    }

    @Override
    public TabulatedFunction create(Function func, double xFrom, double xTo, int count) {
        return  TabulatedFunctions.tabulate(func, xFrom, xTo, count);
    }

    @Override
    public TabulatedFunction create(FunctionPoint[] point) {
        return new LinkedListTabulatedFunction(point);
    }
}
