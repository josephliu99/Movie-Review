package gui;

import model.Event;
import model.EventLog;
import model.Review;
import model.ReviewsList;
import model.exceptions.OutOfBoundException;
import org.json.JSONException;
import persistance.JsonReader;
import persistance.JsonWriter;
//import sun.java2d.pipe.hw.AccelDeviceEventNotifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// MenuGui is the main frame and the main menu of the application
public class MenuGui extends JFrame implements ActionListener {

    private static final String DESTINATION = "./data/reviewslist.json";
    JsonWriter writer = new JsonWriter(DESTINATION);
    JsonReader reader = new JsonReader(DESTINATION);

    Review review;
    ReviewsList reviewsList = new ReviewsList();

    private JButton newReview;
    private JButton viewList;
    private JButton viewReview;
    private JButton loadReview;
    private JButton deleteAllReviews;

    private JPanel newEntryPanel;

    // Initial Constructor that creates main menu with different menu options
    public MenuGui() {
        super("Your Movie Reviews");

        setLayout(new GridLayout(0, 1));
        setMinimumSize(new Dimension(400, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                EventLog el = EventLog.getInstance();
                for (Event next : el) {
                    System.out.println(next.toString() + "\n");
                }
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMenu();
    }

    // Constructor for when a new review entry returns
    public MenuGui(Review review) {
        super("Your Movie Reviews");

        setLayout(new GridLayout(0, 1));
        setMinimumSize(new Dimension(400, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.review = review;
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                EventLog el = EventLog.getInstance();
//                for (Event next : el) {
//                    System.out.println(next.toString());
//                }
//            }
//        });

        readReviewsList();
        reviewsList.addEntry(review);
        writeReviewsList();
        setMenu();
    }


    // MODIFIES: this
    // EFFECTS: Creates buttons(with listeners) and add to main frame
    public void setMenu() {
        JLabel title = new JLabel("Your Movie Reviews");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);

        newReview = new JButton("Write a Review");
        newReview.addActionListener(this);

        viewList = new JButton("Movies You've Watched");
        viewList.addActionListener(this);

        viewReview = new JButton("View Past Reviews");
        viewReview.addActionListener(this);

        loadReview = new JButton("Load Saved Reviews");
        loadReview.addActionListener(this);

        deleteAllReviews = new JButton("Delete All Saved Reviews");
        deleteAllReviews.addActionListener(this);

        this.add(title);
        this.add(newReview);
        this.add(viewList);
        this.add(viewReview);
        this.add(loadReview);
        this.add(deleteAllReviews);
        validate();

    }


    // EFFECTS: Exits current frame and enters the frame for writing a new review
    public void createNewReview() {
        dispose();
        new NewReviewGui();
    }


    // EFFECTS: Create a pop-up window showing a list of movies reviewed. Show an error message if there's no entry yet.
    public void createMovieList() {
        if (reviewsList.getSize() != 0) {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 1));
            JLabel header = new JLabel("Movies That You Have Reviewed:");
            header.setFont(new Font("Arial", Font.BOLD, 14));
            header.setHorizontalAlignment(JLabel.CENTER);
            panel.add(header);

            for (int i = 0; i < reviewsList.getSize(); i++) {
                JLabel movieName = new JLabel(reviewsList.get(i).getMovie().getTitle());
                panel.add(movieName);
            }

            JOptionPane.showMessageDialog(frame, panel, "Movies", JOptionPane.PLAIN_MESSAGE);
        } else {
            printErrorMessage("Error", "No Reviews Yet." + "\nPlease Add a New Review to Continue!");
        }

    }

    // EFFECTS: Allow user choose a review from the past and view it in detail in a new pop-up window.
    //          Show an error message if there's no entry yet.
    public void viewPastReviews() {
        Object[] reviews = reviewsList.reviewToObjectArray();
        int count = 0;

        String[] movieNames = new String[reviewsList.getSize()];
        for (int i = 0; i < reviewsList.getSize(); i++) {
            movieNames[i] = reviewsList.get(i).getMovie().getTitle();
            count++;
        }

        if (reviewsList.getSize() != 0) {

            JFrame frame = new JFrame();
            String name = (String) JOptionPane.showInputDialog(
                    frame, "Choose from the following:", "Past Reviews", JOptionPane.PLAIN_MESSAGE, null,
                    movieNames, null);
            if (name != null) {
                review = (Review) reviews[count - 1];
                JFrame newFrame = new JFrame();
                createNewEntryPanel();
                JOptionPane.showMessageDialog(newFrame, newEntryPanel, "Review", JOptionPane.PLAIN_MESSAGE);

            }
        } else {
            printErrorMessage("Error", "No Reviews Yet." + "\nPlease Add a New Review to Continue!");
        }
    }

    // MODIFIES: newEntryPanel
    // EFFECTS: Initialize newEntryPanel for the pop-up window of viewPastReviews() method
    public void createNewEntryPanel() {
        newEntryPanel = new JPanel();
        newEntryPanel.setLayout(new GridLayout(0, 2));
        newEntryPanel.add(new JLabel("Movie Name:"));
        newEntryPanel.add(new JLabel(review.getMovie().getTitle()));
        newEntryPanel.add(new JLabel("Movie Length"));
        newEntryPanel.add(new JLabel(Integer.toString(review.getMovie().getLength())));
        newEntryPanel.add(new JLabel("Genre:"));
        newEntryPanel.add(new JLabel(review.getMovie().getGenre()));
        newEntryPanel.add(new JLabel("Date of viewing:"));
        newEntryPanel.add(new JLabel(review.getDate()));
        newEntryPanel.add(new JLabel("Rating"));
        newEntryPanel.add(new JLabel(Integer.toString(review.getRating())));
        newEntryPanel.add(new JLabel("Review:"));
        newEntryPanel.add(new JLabel(review.getReview()));
    }

    // EFFECTS: load reviews that were saved in file
    public void loadReviews() {
        try {
            readReviewsList();
            printSuccessMessage("Successfully Loaded Past Reviews");
        } catch (Exception e) {
            printErrorMessage("Error", "File Cannot Load!");
        }

    }

    // MODIFIES: reviewsList
    // EFFECTS: delete all reviews in file
    public void deleteAllReviews() {
        reviewsList = new ReviewsList();
        printSuccessMessage("Successfully deleted all saved reviews.");
        writeReviewsList();
    }

    // EFFECTS: print error message in a pop-up window
    public void printErrorMessage(String error, String message) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                message,
                error,
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: print success message in a pop-up window
    public void printSuccessMessage(String message) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, message);
    }

    // EFFECTS: Read reviews from file
    public void readReviewsList() {
        try {
            reviewsList = reader.read();
        } catch (IOException | OutOfBoundException | JSONException e) {
            printErrorMessage("Read Error", "Invalid Read: Failed to read from file.");
        }
    }

    // MODIFIES: Destination file
    // EFFECTS: Write review to file
    public void writeReviewsList() {
        try {
            writer.open();
            writer.write(reviewsList);
            writer.close();
//            printSuccessMessage("Successfully Saved!");
        } catch (FileNotFoundException e) {
            printErrorMessage("Write Error", "Invalid Write: Failed to write to file");
        }
    }


    // EFFECTS: listens for buttons
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Write a Review":
                createNewReview();
                break;
            case "Movies You've Watched":
                createMovieList();
                break;
            case "View Past Reviews":
                viewPastReviews();
                break;
            case "Load Saved Reviews":
                loadReviews();
                break;
            case "Delete All Saved Reviews":
                deleteAllReviews();
                break;
        }
    }




}
