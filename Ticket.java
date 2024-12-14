package com.mycompany.pp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Ticket {
    private static final PropertyChangeSupport support = new PropertyChangeSupport(Ticket.class);

    public static final int ROWS = 5;
    public static final int COLUMNS = 5;
    private static final boolean[][] bookedSeats = new boolean[ROWS][COLUMNS];
    private static final String[][] reservedBy = new String[ROWS][COLUMNS]; 
    private static final Semaphore semaphore = new Semaphore(1);
    
    public static boolean bookSeat(int row, int column, String userName) {
        boolean permitAcquired = false;
        try {
            permitAcquired = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if (!permitAcquired) {
                System.out.println("Thread " + Thread.currentThread().getName() + " could not acquire the permit for seat [" + row + "][" + column + "]");
                return false;
            }

            if (bookedSeats[row][column]) {
                return false; 
            }
            bookedSeats[row][column] = true;
            reservedBy[row][column] = userName;
            
            support.firePropertyChange("Tickets", null, bookedSeats);

            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (permitAcquired) {
                semaphore.release();
            }
        }
    }
    
    public static String isSeatBooked(int row, int column) {
        try {
            semaphore.acquire();
            return reservedBy[row][column];
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
