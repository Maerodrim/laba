package functions;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.xml.crypto.NoSuchMechanismException;
import java.io.*;
import java.lang.reflect.Constructor;

public class TabulatedFunctions implements  Serializable {
    private static TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(){
        if (TabulatedFunctions.factory instanceof LinkedListTabulatedFunctionFactory) {TabulatedFunctions.factory = new ArrayTabulatedFunctionFactory();}
        else {TabulatedFunctions.factory = new LinkedListTabulatedFunctionFactory();}
    }

    public TabulatedFunctions() {
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        points[0] = new FunctionPoint(leftX, function.getFunctionValue(leftX));

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 1; i < pointsCount; i++) {
            
            points[i] = new FunctionPoint(points[i - 1].pointX + step, function.getFunctionValue(points[i - 1].pointX + step));

        }

        return create(points);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        int pointCount = function.getPointCount();
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(pointCount);

        for (int i = 0; i < pointCount; i++) {
            stream.writeDouble(function.getPointX(i));
            stream.writeDouble(function.getPointY(i));
        }
        stream.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream stream = new DataInputStream(in);
        int pointCount = stream.readInt();

        FunctionPoint points[] = new FunctionPoint[pointCount];

        for (int i = 0; i < pointCount; i++) {
            points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
        }

        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        PrintWriter writer = new PrintWriter(out);
        int pointCount = function.getPointCount();

        writer.println(pointCount);

        for (int i = 0; i < pointCount; i++) {
            writer.println(function.getPointX(i));
            writer.println(function.getPointY(i));
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();

        int pointCount = (int) tokenizer.nval;

        FunctionPoint points[] = new FunctionPoint[pointCount];
        double x, y;
        for (int i = 0; i < pointCount; i++) {
            tokenizer.nextToken();
            x = tokenizer.nval;
            tokenizer.nextToken();
            y = tokenizer.nval;
            
            points[i] = new FunctionPoint(x, y);
        }

        return new LinkedListTabulatedFunction(points);

    }

    public static TabulatedFunction create(double[] xValues, double[] yValues) {
        return factory.create(xValues,yValues);
    }
    public static TabulatedFunction create(FunctionPoint[] point) {
        return factory.create(point);
    }
    public static TabulatedFunction create(double xFrom, double xTo, int count) {
        return factory.create(xFrom,xTo,count);
    }

    public static TabulatedFunction create(Function func, double xFrom, double xTo, int count) {
        return  factory.create(func,xFrom,xTo,count);
    }
    public static TabulatedFunction create(Class<? extends TabulatedFunction> сlas, double[] xValues, double[] yValues) {
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

    public static TabulatedFunction create(Class<? extends TabulatedFunction> сlas, double xFrom, double xTo, int count) {
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

    public static TabulatedFunction create(Class<? extends TabulatedFunction> clas, FunctionPoint[] points) {
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
}
