package com.mycompany.pp;  

import java.beans.PropertyChangeListener;  
import java.beans.PropertyChangeSupport;  
import java.util.concurrent.locks.ReentrantLock;  
import java.util.concurrent.TimeUnit;  

public class Ticket {  

    // PropertyChangeSupport to notify GUI threads  
    private static final PropertyChangeSupport support = new PropertyChangeSupport(Ticket.class);  

    public static final int ROWS = 5;  
    public static final int COLUMNS = 5;  
    private static final boolean[][] bookedSeats = new boolean[ROWS][COLUMNS];  
    private static final String[][] reservedBy = new String[ROWS][COLUMNS]; // Tracks who reserved each seat  
    private static final ReentrantLock lock = new ReentrantLock();  

    // Method to book a seat  
    public static boolean bookSeat(int row, int column, String userName) {  
        boolean lockAcquired = false;  
        try {  
            // Attempt to acquire the lock with a timeout  
            lockAcquired = lock.tryLock(1, TimeUnit.SECONDS);  
            if (!lockAcquired) {  
                // If the lock is not acquired, return false to indicate failure  
                System.out.println("Thread " + Thread.currentThread().getName() + " could not acquire the lock for seat [" + row + "][" + column + "]");  
                return false;  
            }  

            // Critical section: Check and update the seat state  
            if (bookedSeats[row][column]) {  
                return false; // Seat is already booked  
            }  
            bookedSeats[row][column] = true;  
            reservedBy[row][column] = userName; // Store the user who reserved the seat  

            // Notify listeners about the change  
            support.firePropertyChange("Tickets", null, bookedSeats);  

            return true;  
        } catch (InterruptedException e) {  
            // Handle thread interruption (e.g., if the thread is interrupted while waiting for the lock)  
            Thread.currentThread().interrupt();  
            return false;  
        } finally {  
            if (lockAcquired) {  
                lock.unlock(); // Ensure the lock is released  
            }  
        }  
    }  

    // Method to check if a seat is booked and by whom  
    public static String isSeatBooked(int row, int column) {  
        lock.lock();  
        try {  
            return reservedBy[row][column]; // Return the name of the user who reserved the seat (or null if not booked)  
        } finally {  
            lock.unlock();  
        }  
    }  

    // Add a property change listener  
    public static void addPropertyChangeListener(PropertyChangeListener listener) {  
        support.addPropertyChangeListener(listener);  
    }  

    // Remove a property change listener  
    public static void removePropertyChangeListener(PropertyChangeListener listener) {  
        support.removePropertyChangeListener(listener);  
    }  
}