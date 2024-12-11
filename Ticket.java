package com.mycompany.pp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Semaphore;

public class Ticket {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private static final boolean[][] bookedSeats = new boolean[ROWS][COLUMNS];
    private static final Semaphore[][] seatLocks = new Semaphore[ROWS][COLUMNS];
    private static final PropertyChangeSupport support = new PropertyChangeSupport(Ticket.class);

    static {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                seatLocks[i][j] = new Semaphore(1);
            }
        }
    }
    public static boolean bookSeat(int row, int column) {
        try {
            seatLocks[row][column].acquire();
            if (bookedSeats[row][column]) {
                return false; // Seat is already booked
            }
            bookedSeats[row][column] = true;
            support.firePropertyChange("seatUpdate", null, bookedSeats);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            seatLocks[row][column].release();
        }
    }
    public static boolean isSeatBooked(int row, int column) {
        try {
            seatLocks[row][column].acquire();
            return bookedSeats[row][column];
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            seatLocks[row][column].release();
        }
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public static boolean[][] getSeats() {
        return bookedSeats;
    }
}

