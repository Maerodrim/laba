package functions.factory;

import functions.Function;
import functions.FunctionPoint;
import functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    TabulatedFunction create(double xFrom, double xTo, int count);

    TabulatedFunction create(Function func,double xFrom, double xTo, int count);

    TabulatedFunction create(FunctionPoint[] point);

}
