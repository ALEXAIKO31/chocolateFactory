import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Created by is693264 on 29/10/2015.
 */
public class ChocolateBoilerTest {
    public ChocolateBoiler[] chocolateBoiler = new ChocolateBoiler[100];
    public ChocolateBoiler chocolateBoilerTemp;
    public Thread[] thread = new Thread[100];
    volatile int x,y,z,w;
    @Test
    public void testThreadSafe() {

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(101);
        final CyclicBarrier cyclicBarrier1 = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            thread[i] = new Thread() {
                public void run() {
                    try {
                        cyclicBarrier.await();
                        chocolateBoiler[x++] = ChocolateBoiler.getInstance();
                        cyclicBarrier1.await();
                        chocolateBoiler[w++].fill();
                        chocolateBoiler[y++].boil();
                        chocolateBoiler[z++].drain();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        for (int i = 0; i < 100; i++) {
            thread[i].start();
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        try {
            cyclicBarrier1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        chocolateBoilerTemp= ChocolateBoiler.getInstance();
        for (int i = 0; i < 100; i++) {
            assertSame(chocolateBoiler[i],chocolateBoilerTemp);
        }

        for(int i=0;i<100;i++){
            assertEquals(chocolateBoiler[i].getBoilTrue(),chocolateBoiler[i].getDrainTrue());
            assertEquals(chocolateBoiler[i].getDrainTrue(),chocolateBoiler[i].getFillTrue());
            assertEquals(chocolateBoiler[i].getBoilFalse(),100);
            assertEquals(chocolateBoiler[i].getDrainFalse(),100);
            assertEquals(chocolateBoiler[i].getFillFalse(),100);
        }
    }
}

