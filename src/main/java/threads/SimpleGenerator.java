
package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable {

    private Task t;

    @Override
    public void run() {
        for (int i = 0; i < t.getTasks(); i++) {
            Log l = new Log(1 + (Math.random() * 9));
            synchronized (t) {
                t.func = l;
                t.leftX = Math.random() * 100;
                t.rigthX = Math.random() * 100 + 100;
                t.step = Math.random();
                System.out.println("Source leftX = " + t.leftX + " rightX = " + t.rigthX + " step = " + t.step );
            }
            
        }
    }

    public SimpleGenerator(Task ar) {
        this.t = ar;
    }
}
