import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JButton hintButton;
    private JProgressBar progressBar;
    private int progressValue = 0;
    private final int maxAttractions = 3;
    private boolean[] visitedAttractions = new boolean[maxAttractions];
    private JFrame frame;
    private JButton cityMarketButton, waterfrontParkButton, rainbowRowButton, feedbackButton;

    public GUI() {
        frame = new JFrame();

        // Background color
        frame.getContentPane().setBackground(new Color(114, 47, 55)); // Wine red color

        // Custom colors
        Color wineRed = new Color(114, 47, 55);
        Color cofcGold = new Color(255, 215, 0);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(90, 90, 30, 90));
        panel.setLayout(new GridLayout(0, 1));
        panel.setBackground(wineRed); // Wine red color

        // Welcome intro
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Font style and size
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Center alignment
        welcomeLabel.setForeground(cofcGold);

        // Instruction label
        JLabel displayLabel = new JLabel("Select an attraction to visit:");
        displayLabel.setForeground(cofcGold);

        // Buttons for attractions
        cityMarketButton = new JButton("Charleston City Market");
        waterfrontParkButton = new JButton("Cistern Yard");
        rainbowRowButton = new JButton("Addlestone Library");
        JButton loginButton = new JButton("Login");
        feedbackButton = new JButton("Give Feedback");

        // Disable buttons until login is completed
        disableButtons();

        // Hint button
        hintButton = new JButton("Hint");
        hintButton.setVisible(false); // Initially hidden
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Explore the local history and shops for a true Charleston experience!", "Hint", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Progress bar
        progressBar = new JProgressBar(0, maxAttractions);
        progressBar.setValue(progressValue);
        progressBar.setStringPainted(true);

        // "Map" section added as a box
        JTextArea mapBox = new JTextArea("Map:\n[Map will be displayed here]");
        mapBox.setEditable(false);
        mapBox.setBackground(Color.LIGHT_GRAY);
        mapBox.setForeground(Color.BLACK);
        mapBox.setFont(new Font("Arial", Font.BOLD, 14));
        mapBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Helper function to update progress bar and check if attraction was already visited
        ActionListener attractionClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton sourceButton = (JButton) e.getSource();
                String attractionInfo = "";

                if (sourceButton == cityMarketButton && !visitedAttractions[0]) {
                    attractionInfo = "Charleston City Market - 188 Meeting St, Charleston, SC 29401";
                    visitedAttractions[0] = true;
                    incrementProgress();
                } else if (sourceButton == waterfrontParkButton && !visitedAttractions[1]) {
                    attractionInfo = "Cistern Yard - 66 George St, Charleston, SC 29424";
                    visitedAttractions[1] = true;
                    incrementProgress();
                } else if (sourceButton == rainbowRowButton && !visitedAttractions[2]) {
                    attractionInfo = "Addlestone Library - 205 Calhoun St, Charleston, SC 29401";
                    visitedAttractions[2] = true;
                    incrementProgress();
                }

                // Update display label to show attraction info and "beware of your surroundings!"
                displayLabel.setText("<html>" + attractionInfo + "<br>Beware of your surroundings!</html>");
                hintButton.setVisible(true); // Show the hint button after an attraction is selected
            }
        };

        // Attach the same action listener for all attraction buttons
        cityMarketButton.addActionListener(attractionClickListener);
        waterfrontParkButton.addActionListener(attractionClickListener);
        rainbowRowButton.addActionListener(attractionClickListener);

        // Action for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loginInput = JOptionPane.showInputDialog(frame, "Login to unlock attractions. Enter your login details:");
                if (loginInput != null && !loginInput.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Login successful: " + loginInput, "Login", JOptionPane.INFORMATION_MESSAGE);
                    enableButtons(); // Enable the buttons after successful login
                } else {
                    JOptionPane.showMessageDialog(frame, "No login details entered.", "Login", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Action for the feedback button
        feedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedbackInput = JOptionPane.showInputDialog(frame, "Enter your feedback:");
                if (feedbackInput != null && !feedbackInput.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Feedback submitted: " + feedbackInput, "Feedback", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No feedback entered.", "Feedback", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Add components to the panel
        panel.add(welcomeLabel);
        panel.add(loginButton);
        panel.add(cityMarketButton);
        panel.add(waterfrontParkButton);
        panel.add(rainbowRowButton);
        panel.add(feedbackButton);
        panel.add(hintButton);
        panel.add(progressBar);
        panel.add(displayLabel);
        panel.add(mapBox); // Add the map section

        // Final frame settings
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Charleston Attractions!");
        frame.pack();
        frame.setVisible(true);
    }

    // Increment the progress bar by 1 step for each new attraction visited
    private void incrementProgress() {
        progressValue++;
        progressBar.setValue(progressValue);

        if (progressValue == maxAttractions) {
            showCompletionScreen();
        }
    }

    // Display "Storyline Complete!" screen
    private void showCompletionScreen() {
        frame.getContentPane().removeAll(); // Remove all existing components
        JPanel completionPanel = new JPanel();
        completionPanel.setLayout(new BorderLayout());
        completionPanel.setBackground(new Color(114, 47, 55)); // Wine red color

        JLabel completionLabel = new JLabel("Storyline Complete!");
        completionLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Font style and size
        completionLabel.setForeground(new Color(255, 215, 0)); // CofC gold color
        completionLabel.setHorizontalAlignment(JLabel.CENTER); // Center alignment

        completionPanel.add(completionLabel, BorderLayout.CENTER);
        frame.add(completionPanel);
        frame.revalidate(); // Refresh the frame
        frame.repaint();    // Repaint to show changes
    }

    // Disable buttons until login is completed
    private void disableButtons() {
        cityMarketButton.setEnabled(false);
        waterfrontParkButton.setEnabled(false);
        rainbowRowButton.setEnabled(false);
        feedbackButton.setEnabled(false);
    }

    // Enable buttons after successful login
    private void enableButtons() {
        cityMarketButton.setEnabled(true);
        waterfrontParkButton.setEnabled(true);
        rainbowRowButton.setEnabled(true);
        feedbackButton.setEnabled(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
