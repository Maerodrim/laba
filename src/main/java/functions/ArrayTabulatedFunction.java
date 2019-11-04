package functions;

import java.io.Serializable;
import java.util.Arrays;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {
    private FunctionPoint[] valuesArray;
    private int pointCount;

    public ArrayTabulatedFunction(FunctionPoint[] array) {

        if (array.length < 2) {
            throw new IllegalArgumentException();
        }
        pointCount = array.length;
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1].pointX >= array[i].pointX) {
                throw new IllegalArgumentException();
            }
        }
        valuesArray = new FunctionPoint[array.length + array.length / 2];
        System.arraycopy(array, 0, valuesArray, 0, array.length);
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] valuesY) {
        this.pointCount = valuesY.length;
        this.valuesArray = new FunctionPoint[pointCount];
        double buff = leftX;
        double step = (rightX - leftX) / (pointCount - 1);
        if (leftX != rightX) {
            for (int i = 0; i < pointCount; i++) {
                valuesArray[i] = new FunctionPoint(buff, valuesY[i]);
                buff += step;
            }
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.pointCount = pointsCount;
        this.valuesArray = new FunctionPoint[pointCount];
        double buff = leftX;
        double step = (rightX - leftX) / (pointCount - 1);
        if (leftX != rightX) {
            for (int i = 0; i < pointCount; i++) {
                valuesArray[i] = new FunctionPoint(buff, 0);
                buff += step;
            }
        }
    }

    public double getLeftDomainBorder() {
        return valuesArray[0].pointX;
    }

    public double getRightDomainBorder() {
        return valuesArray[pointCount - 1].pointX;
    }

    public double getFunctionValue(double x) {
        if (x > valuesArray[pointCount - 1].pointX || x < valuesArray[0].pointX) {
            return Double.NaN;
        } else {
            if (indexOfX(x) != -1) {
                return getPointY(indexOfX(x));
            } else {
                return interpolate(x, floorIndexOfX(x));
            }
        }
    }

    protected int floorIndexOfX(double x) { // возвращает индекс предыдущего
        int i;
        if (x < getLeftDomainBorder()) {
            return 0;
        }
        for (i = 0; i < pointCount; i++) {
            if (valuesArray[i].pointX > x) {
                return i - 1;
            }
        }
        return pointCount;
    }

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    protected double interpolate(double x, int floorIndex) {
        if (pointCount == 1) {
            return x;
        }
        return interpolate(x, valuesArray[floorIndex].pointX, valuesArray[floorIndex + 1].pointX, valuesArray[floorIndex].pointY, valuesArray[floorIndex + 1].pointY);
    }

    public int getPointCount() {
        return pointCount;
    }

    public double getPointX(int index) {
        if (index < 0 || index >= pointCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return valuesArray[index].pointX;
    }

    public double getPointY(int index) {
        if (index < 0 || index >= pointCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return valuesArray[index].pointY;
    }

    public void setPointY(int index, double value) {
        if (index < 0 || index >= pointCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        valuesArray[index].pointY = value;
    }

    public void setPoint(int index, FunctionPoint value) {
        if (index < 0 || index >= pointCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (value.pointX < valuesArray[pointCount - 1].pointX || value.pointX > valuesArray[0].pointX) {
            valuesArray[index] = value;
        }
    }

    public void setPointX(int index, double value) {
        if (index < 0 || index >= pointCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (value < valuesArray[pointCount - 1].pointX && value > valuesArray[0].pointX) {
            valuesArray[index].pointX = value;
        }
    }

    public int indexOfX(double x) { // возвращает индекс х
        int i;
        for (i = 0; i < pointCount; i++) {
            if (valuesArray[i].pointY == x) {
                return i;
            }
        }
        return -1;
    }

    public int indexOfY(double y) {
        int i;
        for (i = 0; i < pointCount; i++) {
            if (valuesArray[i].pointY == y) {
                return i;
            }
        }
        return -1;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (point.pointX < valuesArray[0].pointX) {
            throw new InappropriateFunctionPointException();
        }
        if (indexOfX(point.pointX) != -1) {
            setPointY(indexOfX(point.pointX), point.pointY);
        } else {
            int index = floorIndexOfX(point.pointX);
            FunctionPoint[] tmp = new FunctionPoint[pointCount + 1];
            if (index == 0) {
                tmp[0] = point;
                System.arraycopy(valuesArray, 0, tmp, 1, pointCount);
                pointCount++;
            } else if (index == pointCount) {
                System.arraycopy(valuesArray, 0, tmp, 0, pointCount);
                tmp[pointCount] = point;
                pointCount++;
            } else {
                System.arraycopy(valuesArray, 0, tmp, 0, index);
                tmp[index] = point;
                System.arraycopy(valuesArray, index, tmp, index + 1, (pointCount - index));
                pointCount++;
            }
            this.valuesArray = tmp;
        }
    }

    public void deletePoint(int index) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointCount) {
            throw new InappropriateFunctionPointException();
        }
        FunctionPoint[] tmp = new FunctionPoint[pointCount - 1];
        if (index == 0) {
            System.arraycopy(valuesArray, 1, tmp, 0, pointCount - 1);
        } else if (index == (pointCount - 1)) {
            System.arraycopy(valuesArray, 0, tmp, 0, pointCount - 1);
        } else {
            System.arraycopy(valuesArray,

                    0, tmp, 0, index);
            System.arraycopy(valuesArray, index + 1, tmp, index, (pointCount - index - 1));
        }
        pointCount--;
        this.valuesArray = tmp;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("{");
        for (int i = 0; i < this.pointCount; i++) {
            buffer.append(this.valuesArray[i].toString()).append(", ");
        }
        buffer.deleteCharAt(buffer.length() - 1).deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();

    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction arrayTabulatedFunction = (ArrayTabulatedFunction) o;
            if (arrayTabulatedFunction.pointCount == this.pointCount) {
                for (int i = 0; i < this.pointCount; i++) {
                    if (this.valuesArray[i].equals(arrayTabulatedFunction.valuesArray[i]) ) {
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else{
            TabulatedFunction func = (TabulatedFunction) o;
            if (func.getPointCount() != this.pointCount) {
                return false;
            }
            for (int i = 0; i < this.pointCount; i++) {
                if (!(this.valuesArray[i].equals(new FunctionPoint(func.getPointX(i), func.getPointY(i))))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Arrays.deepHashCode(this.valuesArray);
        hash = 31 * hash + this.pointCount;
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint valuesArray[] = new FunctionPoint[pointCount];
        for (int i = 0; i < this.pointCount; i++) {
            valuesArray[i] = (FunctionPoint) this.valuesArray[i].clone();

        }
        return new ArrayTabulatedFunction(valuesArray);
    }
}

