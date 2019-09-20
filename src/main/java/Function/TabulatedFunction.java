package Function;

import java.util.Arrays;

public class TabulatedFunction {
    private double[] xValues;
    private double[] yValues;
    private int count;

    TabulatedFunction(double leftX, double rightX, double[] values) {
        this.count = values.length;
        this.yValues = Arrays.copyOf(values, count);
        xValues = new double[count];
        double buff = leftX;
        double step = (rightX - leftX) / (count - 1);
        if (leftX != rightX) {
            for (int i = 0; i < count; i++) {
                xValues[i] = buff;
                buff += step;
            }
        }
    }

    TabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.count = pointsCount;
        xValues = new double[count];
        yValues = new double[count];
        double buff = leftX;
        double step = (rightX - leftX) / (count - 1);
        if (leftX != rightX) {
            for (int i = 0; i < count; i++) {
                xValues[i] = buff;
                yValues[i] = 0;
                buff += step;
            }
        }
    }
}

