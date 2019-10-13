package Function;

public interface TabulatedFunction {
    int getPointCount();

    double getPointX(int index) throws RuntimeException;

    double getPointY(int index) throws RuntimeException;

    void setPointY(int index, double value) throws RuntimeException;

    int indexOfX(double x);

    double getLeftDomainBorder();

    double getRightDomainBorder();

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    public void deletePoint(int index) throws InappropriateFunctionPointException;
}
