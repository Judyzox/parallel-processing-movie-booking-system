package com.mycompany.pp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class gui extends Thread implements PropertyChangeListener {
    private JButton[][] seatButtons;
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;

    public gui() {
    }

    @Override
    public void run() {
        JFrame frame = new JFrame(" movie Ticket Booking ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel headerLabel = new JLabel("Welcome to Cinema Booking!");
        headerLabel.setForeground(Color.YELLOW);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        JPanel seatPanel = new JPanel(new GridLayout(ROWS, COLUMNS, 5, 5));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        seatButtons = new JButton[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JButton seatButton = new JButton((i * COLUMNS + j + 1) + "");
                seatButton.setBackground(Color.GREEN);
                seatButton.setOpaque(true);
                seatButton.setFont(new Font("SansSerif", Font.BOLD, 14));
                seatButton.addActionListener(new SeatBookingActionListener(i, j));
                seatButtons[i][j] = seatButton;
                seatPanel.add(seatButton);
            }
        }

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(seatPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        Ticket.addPropertyChangeListener(this);
        updateSeats(Ticket.getSeats());
    }

    private class SeatBookingActionListener implements ActionListener {
        private final int row;
        private final int column;

        public SeatBookingActionListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton seatButton = seatButtons[row][column];

            if (Ticket.isSeatBooked(row, column)) {
                JOptionPane.showMessageDialog(null, "Sorry, seat " + seatButton.getText() + " is already booked!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isBooked = Ticket.bookSeat(row, column);
                if (isBooked) {
                    seatButton.setBackground(Color.RED); // Set the seat color to red
                    JOptionPane.showMessageDialog(null, "Seat " + seatButton.getText() + " booked successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to book seat! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("seatUpdate".equals(evt.getPropertyName())) {
            boolean[][] seats = (boolean[][]) evt.getNewValue();
            updateSeats(seats);
        }
    }

    private void updateSeats(boolean[][] seats) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (seats[i][j]) {
                    seatButtons[i][j].setBackground(Color.RED);
                }
            }
        }
    }
}
