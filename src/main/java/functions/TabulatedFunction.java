package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable{
    int getPointCount();

    double getPointX(int index) throws RuntimeException;

    double getPointY(int index) throws RuntimeException;

    void setPointY(int index, double value) throws RuntimeException;

    int indexOfX(double x);

    public Object clone() throws CloneNotSupportedException;

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    public void deletePoint(int index) throws InappropriateFunctionPointException;
}
