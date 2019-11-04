import functions.*;
import functions.basic.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws InappropriateFunctionPointException, CloneNotSupportedException {

        double[] arr = {-1, 0, 1 , 2};
        double[] arr2 = {0, 1, 2 , 3};
        TabulatedFunction tab1, tab3, tab2, tab4;
        System.out.println("Массив");
        tab1 = new ArrayTabulatedFunction(0, 3, arr);
        tab2 = new ArrayTabulatedFunction(0, 3, arr);
        for (int i = 0; i < tab1.getPointCount(); i++) {
            System.out.println("Значение в точке x=" + i + ": " + tab1.getPointY(i));
        }
        System.out.println(tab1.toString());
        System.out.println("Список");
        tab3 = new LinkedListTabulatedFunction(arr2, arr);
        tab4 = new LinkedListTabulatedFunction(arr2, arr);
        for (int i = 0; i < tab1.getPointCount(); i++) {
            System.out.println("Значение в точке x=" + i + ": " + tab3.getPointY(i));
        }
        System.out.println(tab3.toString());
        if (tab4.equals(tab3)) {
            System.out.println("Равны");
        } else {
            System.out.println("Не равны");
        }
        System.out.println(tab1.hashCode());
        System.out.println(tab2.hashCode());
        System.out.println(tab3.hashCode());
        System.out.println(tab4.hashCode());
        tab1.setPointY(1, 0.99);
        System.out.println(tab1.hashCode());
        System.out.println("Клонирование объектов");
        TabulatedFunction tab1c, tab2c;
        System.out.println(tab1.toString());
        System.out.println(tab3.toString());
        tab2c = (LinkedListTabulatedFunction) tab3.clone();
        tab1c = (ArrayTabulatedFunction) tab1.clone();
        tab1.setPointY(1, 0.5);
        System.out.println(tab1.toString());
        System.out.println(tab1c.toString());
        System.out.println(tab3.toString());
        tab3.setPointY(1, 0.5);
        System.out.println(tab3.toString());
        System.out.println(tab2c.toString());
    }

}
