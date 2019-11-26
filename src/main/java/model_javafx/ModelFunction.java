package model_javafx;

import functions.TabulatedFunction;

import java.awt.*;

public class ModelFunction  {
    private TabulatedFunction x;

    public ModelFunction() {
    }

    public ModelFunction(TabulatedFunction x) {
        this.x = x;
    }

    public TabulatedFunction getX() {
        return x;
    }

    public void setX(TabulatedFunction x) {
        this.x = x;
    }

}
