/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pp;

/**
 *
 * @author sara
 */
import java.beans.PropertyChangeListener;  
import java.beans.PropertyChangeSupport;  

public class Ticket {  
    public static int Tickets = 5;  

    // PropertyChangeSupport to notify GUI threads  
    private static final PropertyChangeSupport support = new PropertyChangeSupport(Ticket.class);  

    public static synchronized void BookTicket(int num) {  
        int oldTickets = Tickets;  
        Tickets -= num;  
        // Notify listeners about the change  
        support.firePropertyChange("Tickets", oldTickets, Tickets);  
    }  

    public static synchronized int getTickets() {  
        return Tickets;  
    }  

    public static void addPropertyChangeListener(PropertyChangeListener listener) {  
        support.addPropertyChangeListener(listener);  
    }  

    public static void removePropertyChangeListener(PropertyChangeListener listener) {  
        support.removePropertyChangeListener(listener);  
    }  
}