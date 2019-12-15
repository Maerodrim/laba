package functions;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.xml.crypto.NoSuchMechanismException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

public interface TabulatedFunction extends Function, Serializable, Iterable<FunctionPoint>{
    TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();

    default TabulatedFunctionFactory setTabulatedFunctionFactory(){
        if (this instanceof LinkedListTabulatedFunction) return  new ArrayTabulatedFunctionFactory();
      return  new LinkedListTabulatedFunctionFactory();
    }

    default TabulatedFunction create(double[] xValues, double[] yValues) {
        return factory.create(xValues,yValues);
    }

    default TabulatedFunction create(double xFrom, double xTo, int count) {
        return factory.create(xFrom,xTo,count);
    }

    default TabulatedFunction create(Function func, double xFrom, double xTo, int count) {
        return  factory.create(func,xFrom,xTo,count);
    }
    default TabulatedFunction create(Class<? extends TabulatedFunction> сlas,double[] xValues, double[] yValues) {
        Constructor constructors[] = сlas.getConstructors();
        for (Constructor constructor : constructors) {
            Class types[] = constructor.getParameterTypes();
            if (types.length == 2 && types[0].equals(xValues.getClass()) && types[1].equals(yValues.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(xValues, yValues);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        throw new NoSuchMechanismException();
    }

    default TabulatedFunction create(Class<? extends TabulatedFunction> сlas,double xFrom, double xTo, int count) {
        Constructor constructors[] = сlas.getConstructors();
        for (Constructor constructor : constructors) {
            Class types[] = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(Integer.TYPE)) {
                try {
                    return (TabulatedFunction) constructor.newInstance(xFrom, xTo, count);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        throw new NoSuchMechanismException();
    }

    default TabulatedFunction create(Class<? extends TabulatedFunction> clas, FunctionPoint[] points) {
        Constructor constructors[] = clas.getConstructors();
        for (Constructor constructor : constructors) {
            Class types[] = constructor.getParameterTypes();
            if (types[0].equals(points.getClass())) {
                try {

                    return (TabulatedFunction) constructor.newInstance(new Object[]{points});
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        throw new NoSuchMechanismException();
    }

    int getPointCount();

    double getPointX(int index) throws RuntimeException;

    double getPointY(int index) throws RuntimeException;

    void setPointY(int index, double value) throws RuntimeException;

    int indexOfX(double x);

    public Object clone() throws CloneNotSupportedException;

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    public void deletePoint(int index) throws InappropriateFunctionPointException;
}
