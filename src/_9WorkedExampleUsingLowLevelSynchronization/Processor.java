package _9WorkedExampleUsingLowLevelSynchronization;




import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Processor {

    private List<Integer> list = new ArrayList<Integer>();
    private final int LIMIT = 10;
    Random random = new Random();
    private Object lock = new Object();


    public void produce() throws  InterruptedException {
        int value = 0;
        while (true) {
            synchronized (this) {

                if (list.size() == LIMIT) {
//                    lock.wait();
                    wait();
                }
                list.add(value++);
//                lock.notify();
                notify();
            }
        }
    }

    public void consume() throws InterruptedException{

        while (true){
            synchronized (this){


                if (list.size() == 0){
//                    lock.wait();
                    wait();
                }
                Integer value = list.remove(0);
//                lock.notify();
                notify();
                System.out.println("List size: " + list.size() + " value: " + value);
            }

            Thread.sleep(random.nextInt(1000));
        }
    }


}
