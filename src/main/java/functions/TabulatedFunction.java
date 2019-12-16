package functions;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.xml.crypto.NoSuchMechanismException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

public interface TabulatedFunction extends Function, Serializable, Iterable<FunctionPoint>{

    int getPointCount();

    double getPointX(int index) throws RuntimeException;

    double getPointY(int index) throws RuntimeException;

    void setPointY(int index, double value) throws RuntimeException;

    int indexOfX(double x);

    public Object clone() throws CloneNotSupportedException;

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    public void deletePoint(int index) throws InappropriateFunctionPointException;
}
