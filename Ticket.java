package com.mycompany.pp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Ticket {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private static boolean[][] bookedSeats = new boolean[ROWS][COLUMNS];
    private static final PropertyChangeSupport support = new PropertyChangeSupport(Ticket.class);

    // Book a seat
    public static synchronized boolean bookSeat(int row, int column) {
        if (bookedSeats[row][column]) {
            return false; // Seat is already booked
        }
        bookedSeats[row][column] = true;
        support.firePropertyChange("seatUpdate", null, bookedSeats);
        return true;
    }

    // Check if a seat is booked
    public static boolean isSeatBooked(int row, int column) {
        return bookedSeats[row][column];
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
