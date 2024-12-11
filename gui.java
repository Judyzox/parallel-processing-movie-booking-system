package com.mycompany.pp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class gui extends Thread implements PropertyChangeListener {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;

    public gui() {
    }

    @Override
    public void run() {
        JFrame mainFrame = new JFrame("Cinema Booking Manager");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new FlowLayout());

        JLabel instructionLabel = new JLabel("Enter number of booking users to book:");
        JTextField userInputField = new JTextField(5);
        JButton openWindowsButton = new JButton("Start Booking");

        openWindowsButton.addActionListener(e -> {
            String input = userInputField.getText();
            try {
                int numberOfWindows = Integer.parseInt(input);
                if (numberOfWindows <= 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (int i = 1; i <= numberOfWindows; i++) {
                        int userNumber = i;
                        SwingUtilities.invokeLater(() -> new BookingWindow(userNumber).start());
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.add(instructionLabel);
        mainFrame.add(userInputField);
        mainFrame.add(openWindowsButton);
        mainFrame.setVisible(true);
    }

    private static class BookingWindow extends Thread {
        private final int userNumber;
        private JButton[][] seatButtons;

        private static final int ROWS = 5;
        private static final int COLUMNS = 5;

        public BookingWindow(int userNumber) {
            this.userNumber = userNumber;
        }

        @Override
        public void run() {
            JFrame frame = new JFrame("Movie Ticket Booking - User " + userNumber);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());

            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(Color.BLACK);
            JLabel headerLabel = new JLabel("Welcome to Cinema Booking for User " + userNumber + "!");
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

            Ticket.addPropertyChangeListener(evt -> {
                if ("seatUpdate".equals(evt.getPropertyName())) {
                    boolean[][] seats = (boolean[][]) evt.getNewValue();
                    updateSeats(seats);
                }
            });
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
                        seatButton.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(null, "Seat " + seatButton.getText() + " booked successfully by user " +userNumber+ "!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to book seat! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
