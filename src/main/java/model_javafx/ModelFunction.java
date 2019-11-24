package model_javafx;

import functions.TabulatedFunction;

import java.awt.*;

public class ModelFunction {
    private TabulatedFunction x;
    private Component y;

    public ModelFunction(TabulatedFunction x, Component y) {
        this.x = x;
        this.y = y;
    }

    public TabulatedFunction getX() {
        return x;
    }

    public void setX(TabulatedFunction x) {
        this.x = x;
    }

    public Component getY() {
        return y;
    }

    public void setY(Component y) {
        this.y = y;
    }
}
