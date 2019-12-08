
package threads;

import functions.Function;

public class Task {

    public Function func;
    public double leftX, rigthX, step;
    private int tasks;

    public Task(int t) {
        if (t<=0) {
            throw new IllegalArgumentException();
        } 
        this.tasks = t;
    }
    
    public int getTasks(){
        return this.tasks;
    }

}
