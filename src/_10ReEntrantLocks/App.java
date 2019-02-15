package _10ReEntrantLocks;

public class App {

    public static void main(String[] args) throws InterruptedException {
        final Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    runner.firstThread();
                }catch (InterruptedException e){
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    runner.secondThread();
                }catch (InterruptedException e){
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finished();

    }
}
