package _11Deadlock;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLock(Lock firstLock, Lock secondLock)
            throws InterruptedException{

        boolean getfirstLock = false;
        boolean getsecondLock = false;

        while (true){

            try{
                getfirstLock = lock1.tryLock();
                getsecondLock = lock2.tryLock();
            }
            finally {
                if (getfirstLock && getsecondLock)
                    return;

                if (getfirstLock){
                    lock1.unlock();
                }

                if (getsecondLock){
                    lock2.unlock();
                }
            }

            Thread.sleep(1);
        }
    }

    public void firstThread()throws InterruptedException{
        Random random = new Random();

        for (int i=0; i<10000; i++){
            acquireLock(lock1, lock2);

            try{
                Account.transfer(account1, account2, random.nextInt(100));
            }
            finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException{
        Random random = new Random();

        for (int i=0; i<10000; i++){
            acquireLock(lock2, lock1);

            try{
                Account.transfer(account2, account1, random.nextInt(100));
            }
            finally {
                lock2.unlock();
                lock1.unlock();
            }
        }
    }

    public void finished(){
        System.out.println("Account1 balance: "+ account1.getBalance());
        System.out.println("Account2 balance: "+ account2.getBalance());
        System.out.println("Total balance: "+
                (account1.getBalance() + account2.getBalance()));
    }
}
