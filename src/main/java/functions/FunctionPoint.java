package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    public double pointX;
    public double pointY;

    public FunctionPoint(double pointX, double pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public FunctionPoint(FunctionPoint point) {
        this.pointX = point.pointX;
        this.pointY = point.pointY;
    }

    public FunctionPoint() {
        this.pointX = 0;
        this.pointY = 0;
    }

    public double getPointX() {
        return pointX;
    }

    public void setPointX(double pointX) {
        this.pointX = pointX;
    }

    public double getPointY() {
        return pointY;
    }

    public void setPointY(double pointY) {
        this.pointY = pointY;
    }

    @Override
    public String toString(){
        return new String("("+this.pointX+"; "+this.pointY+")");
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof FunctionPoint) {
            FunctionPoint point = (FunctionPoint) o;
            if (this.pointX == point.pointX) {
                if (this.pointY == point.pointY) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.pointX) ^ (Double.doubleToLongBits(this.pointX) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.pointY) ^ (Double.doubleToLongBits(this.pointY) >>> 32));
        return hash;
    }

    @Override
    public Object clone() {
        return new FunctionPoint(this);

    }
}
