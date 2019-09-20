package Function;

public class TabulatedFunction {
    private FunctionPoint[] valuesArray;
    private int count;

    TabulatedFunction(double leftX, double rightX, double[] values) {
        this.count = values.length;
        FunctionPoint[] valuesArray = new FunctionPoint[count];
        double buff = leftX;
        double step = (rightX - leftX) / (count - 1);
        if (leftX != rightX) {
            for (int i = 0; i < count; i++) {
                valuesArray[i].pointX = buff;
                valuesArray[i].pointY = values[i];
                buff += step;
            }
        }
    }

    TabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.count = pointsCount;
        FunctionPoint[] valuesArray = new FunctionPoint[count];
        double buff = leftX;
        double step = (rightX - leftX) / (count - 1);
        if (leftX != rightX) {
            for (int i = 0; i < count; i++) {
                valuesArray[i].pointX = buff;
                valuesArray[i].pointY = 0;
                buff += step;
            }
        }
    }

    double getLeftDomainBorder() {
        return valuesArray[0].pointX;
    }

    double getRightDomainBorder() {
        return valuesArray[count - 1].pointX;
    }

    double getFunctionValue(double x) {
        if (x > valuesArray[count - 1].pointX || x < valuesArray[0].pointX) {
            return Double.NaN;
        } else {
            if (x < getLeftDomainBorder()) {
                return extrapolateLeft(x);
            } else if (x > getRightDomainBorder()) {
                return extrapolateRight(x);
            } else if (indexOfX(x) != -1) {
                return getY(indexOfX(x));
            } else {
                return interpolate(x, floorIndexOfX(x));
            }
        }
    }

    protected int floorIndexOfX(double x) {
        int i;
        if (x < getLeftDomainBorder()) {
            return 0;
        }
        for (i = 0; i < count; i++) {
            if (valuesArray[i].pointX > x) {
                return i - 1;
            }
        }
        return count;
    }

    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, valuesArray[0].pointX, valuesArray[1].pointX, valuesArray[0].pointY, valuesArray[1].pointY);
    }


    protected double extrapolateRight(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, valuesArray[count - 2].pointX, valuesArray[count - 1].pointX, valuesArray[count - 2].pointY, valuesArray[count - 1].pointY);
    }


    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, valuesArray[floorIndex].pointX, valuesArray[floorIndex + 1].pointX, valuesArray[floorIndex].pointY, valuesArray[floorIndex + 1].pointY);
    }


    public int getCount() {
        return count;
    }


    public double getX(int index) {
        return valuesArray[index].pointX;
    }


    public double getY(int index) {
        return valuesArray[index].pointY;
    }


    public void setY(int index, double value) {
        valuesArray[index].pointY = value;
    }


    public int indexOfX(double x) {
        int i;
        for (i = 0; i < count; i++) {
            if (valuesArray[i].pointY == x) {
                return i;
            }
        }
        return -1;
    }


    public int indexOfY(double y) {
        int i;
        for (i = 0; i < count; i++) {
            if (valuesArray[i].pointY == y) {
                return i;
            }
        }
        return -1;
    }


    public void addPoint(FunctionPoint point) {
        if (indexOfX(point.pointX) != -1) {
            setY(indexOfX(point.pointX), point.pointY);
        } else {
            int index = floorIndexOfX(point.pointX);
            FunctionPoint[] Tmp = new FunctionPoint[count + 1];
            if (index == 0) {
                Tmp[0] = point;
                System.arraycopy(valuesArray, 0, Tmp, 1, count);
                count++;
            } else if (index == count) {
                System.arraycopy(valuesArray, 0, Tmp, 0, count);
                Tmp[count] = point;
                count++;
            } else {
                System.arraycopy(valuesArray, 0, Tmp, 0, index);
                Tmp[index] = point;
                System.arraycopy(valuesArray, index, Tmp, index + 1, (count - index));
                count++;
            }
            this.valuesArray = Tmp;
        }
    }

    public void deletePoint(int index) {
        FunctionPoint[] Tmp = new FunctionPoint[count - 1];
        if (index == 0) {
            System.arraycopy(valuesArray, 1, Tmp, 0, count - 1);
        } else if (index == (count - 1)) {
            System.arraycopy(valuesArray, 0, Tmp, 0, count - 1);
        } else {
            System.arraycopy(valuesArray, 0, Tmp, 0, index);
            System.arraycopy(valuesArray, index + 1, Tmp, index, (count - index - 1));
        }
        count--;
        this.valuesArray = Tmp;
    }
}

