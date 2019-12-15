package functions;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Iterable<FunctionPoint> {
    private FunctionNode head;
    private int count;

    private static class FunctionNode {
        FunctionNode next;
        FunctionNode prev;
        FunctionPoint point;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] array) {

        if (array.length < 2) {
            throw new IllegalArgumentException();
        }
        this.count = array.length;
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1].pointX >= array[i].pointX) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < count; i++) {
            this.addNode(array[i].pointX, array[i].pointY);
        }
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        this.count = xValues.length;
        for (int i = 0; i < count; i++) {
            this.addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(double xFrom, double xTo, int count) {
        this.count = count;
        if (xFrom > xTo) {
            xFrom = xFrom - xTo;
            xTo = xFrom + xTo;
            xFrom = -xFrom + xTo;
        }
        double step = (xTo - xFrom) / (count - 1);
        double buff = xFrom;
        for (int i = 0; i < count; i++) {
            this.addNode(buff, 0);
            buff += step;
        }
    }

    void addNode(double x, double y) {
        FunctionNode newFunctionNode = new FunctionNode();
        if (head == null) {
            head = newFunctionNode;
            newFunctionNode.next = head;
            newFunctionNode.prev = head;
            newFunctionNode.point = new FunctionPoint(x, y);
        } else {
            newFunctionNode.next = head;
            newFunctionNode.prev = head.prev;
            newFunctionNode.point = new FunctionPoint(x, y);
            head.prev.next = newFunctionNode;
            head.prev = newFunctionNode;
        }
    }

    public int getPointCount() {
        return count;
    }

    public double getLeftDomainBorder() {
        return head.point.pointX;
    }

    public double getRightDomainBorder() {
        return head.prev.point.pointX;
    }

    @Override
    public double getFunctionValue(double x) {
        return getNode(indexOfX(x)).point.pointY;
    }

    private FunctionNode getNode(int index) {
        FunctionNode buff;
        if (index > (count / 2)) {
            buff = head.prev;
            for (int i = count - 1; i > 0; i--) {
                if (i == index) {
                    return buff;
                } else {
                    buff = buff.prev;
                }
            }
        } else {
            buff = head;
            for (int i = 0; i < count; i++) {
                if (index == i) {
                    return buff;
                } else {
                    buff = buff.next;
                }
            }
        }
        return null;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= count) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNode(index).point.pointX;
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= count) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNode(index).point.pointY;
    }

    public void setPointY(int index, double value) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= count) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNode(index).point.pointY = value;
    }

    public void setPoint(int index, FunctionPoint value) {
        if (index < 0 || index >= count) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNode(index).point = value;
    }

    public void setPointX(int index, double value) {
        if (index < 0 || index >= count) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNode(index).point.pointX = value;
    }


    public int indexOfX(double x) {
        FunctionNode buff;
        buff = head;
        for (int i = 0; i < count; i++) {
            if (buff.point.pointX == x) {
                return i;
            } else {
                buff = buff.next;
            }
        }
        return -1;
    }

    public int indexOfY(double y) {
        FunctionNode buff;
        buff = head;
        for (int i = 0; i < count; i++) {
            if (buff.point.pointY == y) {
                return i;
            } else {
                buff = buff.next;
            }
        }
        return -1;
    }

    public int floorIndexOfX(double x) throws InappropriateFunctionPointException {
        FunctionNode buff;
        if (x < head.point.pointX) {
            throw new InappropriateFunctionPointException();
        }
        buff = head;
        for (int i = 0; i < count; i++)
            if (buff.point.pointX < x) {
                buff = buff.next;
            } else {
                return i - 1;
            }
        return getPointCount();
    }

    protected double interpolate(double x, int floorIndex) throws InappropriateFunctionPointException {
        FunctionNode left = getNode(floorIndex);
        FunctionNode right = left.next;
        if (x < left.point.pointX || right.point.pointX < x) {
            throw new InappropriateFunctionPointException();
        }
        return left.point.pointY + (right.point.pointY - left.point.pointY) * (x - left.point.pointX) / (right.point.pointX - left.point.pointX);
    }

    private FunctionNode floorNodeOfX(double x) throws IllegalArgumentException {
        FunctionNode buff;
        if (x < head.point.pointX) {
            throw new IllegalArgumentException("Argument x less than minimal x in tabulated function");
        }
        buff = head;
        for (int i = 0; i < count; i++) {
            if (buff.point.pointX < x) {
                buff = buff.next;
            } else {
                return buff.prev;
            }
        }
        return head.prev;
    }

    private FunctionNode nodeOfX(double x) {
        FunctionNode buff;
        buff = head;
        for (int i = 0; i < count; i++) {
            if (buff.point.pointX == x) {
                return buff;
            } else {
                buff = buff.next;
            }
        }
        return null;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (indexOfX(point.pointX) != -1) {
            setPointY(indexOfX(point.pointX), point.pointY);
        } else {
            int index;
            index = floorIndexOfX(point.pointX);
            FunctionNode newFunctionNode = new FunctionNode();
            if (index == 0 || index == count) {
                newFunctionNode.next = head;
                newFunctionNode.prev = head.prev;
                newFunctionNode.point = point;
                head.prev.next = newFunctionNode;
                head.prev = newFunctionNode;
                if (index == 0) {
                    head = newFunctionNode;
                }
                count++;
            } else {
                FunctionNode previous = getNode(index);
                FunctionNode further = previous.next;
                newFunctionNode.next = further;
                newFunctionNode.prev = previous;
                newFunctionNode.point = point;
                previous.next = newFunctionNode;
                further.prev = newFunctionNode;
                count++;
            }
        }
    }

    public void deletePoint(int index) throws IllegalArgumentException {
        FunctionNode buff;
        buff = getNode(index);
        if (buff == null) {
            throw new IllegalArgumentException("Index is out of bounds");
        }
        FunctionNode previous = buff.prev;
        FunctionNode further = buff.next;
        previous.next = further;
        further.prev = previous;
        count--;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("{");
        for (int i = 0; i < this.count; i++) {
            buffer.append(this.getNode(i).point.toString()).append(", ");
        }
        buffer.deleteCharAt(buffer.length() - 1).deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();

    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListTabulatedFunction) {
            LinkedListTabulatedFunction linkedListTabulatedFunction = (LinkedListTabulatedFunction) o;
            if (linkedListTabulatedFunction.count == this.count) {
                for (int i = 0; i < this.count; i++) {
                    if (this.getNode(i).point.equals(linkedListTabulatedFunction.getNode(i).point)) {
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            TabulatedFunction func = (TabulatedFunction) o;
            if (func.getPointCount() != this.count) {
                return false;
            }
            for (int i = 0; i < this.count; i++) {
                if (!(this.getNode(i).point.equals(new FunctionPoint(func.getPointX(i), func.getPointY(i))))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {

        FunctionNode node = head;

        int result = 0;

        while (node != head) {
            result += node.point.hashCode();
            node = node.next;
        }

        return count + result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint points[] = new FunctionPoint[count];
        FunctionNode node = head;
        for (int i = 0; i < this.count; i++) {
            points[i] = node.point;
            node = node.next;
        }

        return new LinkedListTabulatedFunction(points);

    }

    @Override
    @NotNull
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            private FunctionNode node = head;

            public boolean hasNext() {
                return (node != null);
            }

            public FunctionPoint next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                FunctionPoint point = new FunctionPoint(node.point.pointX, node.point.pointY);
                node = node != head.prev ? node.next : null;
                return point;
            }
        };
    }
}

