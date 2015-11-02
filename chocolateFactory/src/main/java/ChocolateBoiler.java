/**
 * Created by is693264 on 29/10/2015.
 */
public class ChocolateBoiler {

    private volatile boolean empty;
    private volatile boolean boiled;
    private volatile static ChocolateBoiler uniqueInstance;
    private int fillTrue;
    private int fillFalse;
    private int drainTrue;
    private int drainFalse;
    private int boilTrue;
    private int boilFalse;

    private ChocolateBoiler(){
        empty = true;
        boiled = false;
    }

    public static ChocolateBoiler getInstance(){
        if(uniqueInstance== null){
            synchronized (ChocolateBoiler.class){
                if(uniqueInstance== null){
                    uniqueInstance = new ChocolateBoiler();
                }
            }
        }
        return uniqueInstance;
    }

    public synchronized void fill(){
        fillFalse++;
        if(isEmpty()){
            empty = false;
            boiled = false;
            fillTrue++;
        }
    }

    public synchronized void drain(){
        drainFalse++;
        if(!isEmpty()&&isBoiled()){
            empty = true;
            drainTrue++;
        }
    }

    public synchronized void boil(){
        boilFalse++;
        if(!isEmpty() && !isBoiled()){
            boiled = true;
            boilTrue++;
        }
    }

    public boolean isEmpty(){
        return empty;
    }

    public boolean isBoiled(){
        return boiled;
    }

    public int getFillTrue(){
        return fillTrue;
    }

    public int getFillFalse(){
        return fillFalse;
    }

    public int getDrainTrue(){
        return drainTrue;
    }

    public int getDrainFalse(){
        return drainFalse;
    }

    public int getBoilTrue(){
        return boilTrue;
    }

    public int getBoilFalse(){
        return boilFalse;
    }
}
