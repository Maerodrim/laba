package Function;

import java.util.Arrays;

public class TabulatedFunction {
    private double[] xValues;
    private double[] yValues;
    private int count;

    TabulatedFunction(double leftX, double rightX, double[] values) {
        this.count = values.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    TabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.count = pointsCount;
        if (xFrom > xTo) {
            xFrom = xFrom - xTo;
            xTo = xFrom + xTo;
            xFrom = -xFrom + xTo;
        }
        xValues = new double[count];
        yValues = new double[count];
        double buff = xFrom;
        double step = (xTo - xFrom) / (count - 1);
        if (xFrom != xTo) {
            for (int i = 0; i < count; i++) {
                xValues[i] = buff;
                yValues[i] = source.apply(buff);
                buff += step;
            }
        } else {
            double funcXFrom = source.apply(xFrom);
            for (int i = 0; i < count; i++) {
                xValues[i] = xFrom;
                yValues[i] = funcXFrom;
                buff += step;
            }
        }
    }
}
