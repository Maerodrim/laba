
package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {

    private Task t;

    public SimpleIntegrator(Task ar) {
        this.t = ar;
    }

    @Override
    public void run() {
        for (int i = 0; i < t.getTasks(); i++) {
            double res;
            if (t.func == null) {
                continue;
            }
            synchronized (t) {
                res = Functions.integrate(t.func, t.leftX, t.rigthX, t.step);
                System.out.println("Result leftX = " + t.leftX + " rightX = " + t.rigthX + " step = " + t.step + " integrate = " + res );
            }
            
        }
    }

}
