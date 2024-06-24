package gui;

import model.Movie;
import model.Review;
import model.exceptions.OutOfBoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.security.auth.callback.ConfirmationCallback.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

// Frame of when createNewReview() in MenuGui is called, the frame for a new movie review entry
public class NewReviewGui extends JFrame implements ActionListener {

    private JLabel title;


    private JTextField movieNameText;
    private JTextField movieLengthText;
    private JTextField genreText;
    private JTextField dateWatchedText;
    private JTextField ratingText;
    private JTextArea userReviewText;

    private JPanel newEntryPanel;

    protected JButton back;
    protected JButton save;

    Review review = null;

    GridBagConstraints con;

    // Constructor: create frame for a review entry
    public NewReviewGui() {
        super("New Review Entry");
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        con = new GridBagConstraints();
        con.fill = GridBagConstraints.HORIZONTAL;

        createNewReview();
    }

    // MODIFIES: this
    // EFFECTS: sets the main layout for the frame
    public void createNewReview() {
        title = new JLabel("Your Movie Review");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon movieLogo = new ImageIcon(new ImageIcon("./data/movieIcon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JLabel movieImg = new JLabel();
        movieImg.setIcon(movieLogo);
        movieImg.setHorizontalAlignment(JLabel.CENTER);

        add(title,con);
        add(movieImg,con);
        initMovieName();
        initGenre();
        initMovieLength();
        initDateWatched();
        initRating();
        initReview();
        initButton();
        validate();
    }

    // EFFECTS: perform action of saving a review
    public void addEntry() {
        boolean running = true;

        while (running) {
            JFrame frame = new JFrame();
            createNewEntryPanel();
            ImageIcon thumbsup = new ImageIcon(new ImageIcon("./data/thumbsup.png").getImage()
                    .getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            int option = JOptionPane.showConfirmDialog(frame, newEntryPanel,
                    "Your Review Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, thumbsup);

            if (option == YES_OPTION) {
                if (checkIfValidInput(movieLengthText) && checkIfValidInput(ratingText) && checkIfInRange(ratingText)) {
                    newEntry();
                    running = false;
                    JFrame success =  new JFrame();
                    JOptionPane.showMessageDialog(success, "Review Saved Successful");
                    returnToMenu();
                } else {
                    printErrorMessage("Invalid Input", "Movie Length and/or Rating contains invalid input.");
                }
            } else {
                break;
            }
        }
    }

    // EFFECTS: creates a new Review with user's inputs
    public void newEntry() {
        String movieName = movieNameText.getText();
        int movieLength = textToInt(movieLengthText);
        String genre = genreText.getText();

        Movie movie = new Movie(movieName,genre,movieLength);

        String dateWatched = dateWatchedText.getText();
        int rating = textToInt(ratingText);
        String userReview = userReviewText.getText();

        review = new Review(movie, rating, dateWatched, userReview);
    }

    // EFFECTS: returning an integer value from a JTextField
    public int textToInt(JTextField text) {
        return Integer.parseInt(text.getText());
    }

    // EFFECTS: Check if input is a numeric value: true if input fromm JTextField is integer, false otherwise
    private boolean checkIfValidInput(JTextField text) {
        try {
            Integer.parseInt(text.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECTS: Check if rating is in range
    private  boolean checkIfInRange(JTextField rating) {
        int n = Integer.parseInt(rating.getText());
        return n <= 5 && n >= 0;

    }

    // MODIFIES: newEntryPanel
    // EFFECTS: Initialize newEntryPanel for the pop-up window from addEntry()
    public void createNewEntryPanel() {
        newEntryPanel = new JPanel();

//        ImageIcon thumbsup = new ImageIcon(new ImageIcon("./data/thumbsup.png").getImage()
//                .getScaledInstance(20, 20, Image.SCALE_DEFAULT));
//        JLabel icon = new JLabel();
//        icon.setIcon(thumbsup);

        newEntryPanel.setLayout(new GridLayout(0,2));
//        newEntryPanel.add(icon);
        newEntryPanel.add(new JLabel("Movie Name: "));
        newEntryPanel.add(new JLabel(movieNameText.getText()));
        newEntryPanel.add(new JLabel("Movie Length: "));
        newEntryPanel.add(new JLabel(movieLengthText.getText()));
        newEntryPanel.add(new JLabel("Genre: "));
        newEntryPanel.add(new JLabel(genreText.getText()));
        newEntryPanel.add(new JLabel("Date of viewing: "));
        newEntryPanel.add(new JLabel(dateWatchedText.getText()));
        newEntryPanel.add(new JLabel("Rating: "));
        newEntryPanel.add(new JLabel(ratingText.getText()));
        newEntryPanel.add(new JLabel("Review: "));
        newEntryPanel.add(new JLabel(userReviewText.getText()));
    }

    // MODIFIES: this
    // EFFECTS: initialize movie name and its text field
    public void initMovieName() {

        JLabel movieName = new JLabel("Movie Name:");
        movieName.setFont(new Font("Arial", Font.PLAIN, 15));
        movieName.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
        con.weightx = 0.25;
//        con.gridy = 0;
        this.add(movieName, con);

        movieNameText = new JTextField(20);
        con.gridx = 1;
        con.weightx = 0.75;
        this.add(movieNameText, con);

    }

    // MODIFIES: this
    // EFFECTS: initialize genre and its text field
    public void initGenre() {
        JLabel genre = new JLabel("Movie Genre:");
        genre.setFont(new Font("Arial", Font.PLAIN, 15));
        genre.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
//        con.gridy = 1;
        this.add(genre, con);

        genreText = new JTextField(15);
        con.gridx = 1;
        this.add(genreText, con);
    }

    // MODIFIES: this
    // EFFECTS: initialize movie name and its text field
    public void initMovieLength() {
        JLabel movieLength = new JLabel("Movie Length: [minutes]");
        movieLength.setFont(new Font("Arial", Font.PLAIN, 15));
        movieLength.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
//        con.gridy = 2;
        this.add(movieLength, con);

        movieLengthText = new JTextField(10);
        con.gridx = 1;
        this.add(movieLengthText, con);
    }

    // MODIFIES: this
    // EFFECTS: initialize date watched and its text field
    public void initDateWatched() {
        JLabel dateWatched = new JLabel("Date of viewing: [m/d/y]");
        dateWatched.setFont(new Font("Arial", Font.PLAIN, 15));
        dateWatched.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
//        con.gridy = 3;
        this.add(dateWatched, con);

        dateWatchedText = new JTextField(20);
        con.gridx = 1;
        this.add(dateWatchedText, con);

    }

    // MODIFIES: this
    // EFFECTS: initialize rating and its text field
    public void initRating() {
        JLabel rating = new JLabel("Rating: [0-5]");
        rating.setFont(new Font("Arial", Font.PLAIN, 15));
        rating.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
//        con.gridy = 4;
        this.add(rating, con);

        ratingText = new JTextField(10);
        con.gridx = 1;
        this.add(ratingText, con);
    }

    // MODIFIES: this
    // EFFECTS: initialize review and its text area
    public void initReview() {
        JLabel review = new JLabel("Review:");
        review.setFont(new Font("Arial", Font.PLAIN, 15));
        review.setHorizontalAlignment(JLabel.CENTER);
        con.gridx = 0;
//        con.gridy = 5;
        this.add(review, con);

        userReviewText = new JTextArea(20, 20);
        con.gridx = 1;
        this.add(userReviewText, con);
    }

    // MODIFIES: this
    // EFFECTS: initialize all buttons and add listeners
    public void initButton() {
        save = new JButton("Save Review");
        con.gridx = 0;
//        con.gridy = 6;
        save.addActionListener(this);
        this.add(save, con);

        back = new JButton("Back");
        back.addActionListener(this);
        con.fill = GridBagConstraints.RELATIVE;
        con.gridx = 1;
        this.add(back, con);
    }

    // EFFECTS: Exits current frame and return to menu frame, if a review has been created, it will be passed as a
    //          parameter to MenuGui
    public void returnToMenu() {
        dispose();
        if (review != null) {
            new MenuGui(review);
        } else {
            new MenuGui();
        }
    }

    // EFFECTS: print error message
    public void printErrorMessage(String error, String message) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                message,
                error,
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: listener for buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Back":
                returnToMenu();
                break;
            case "Save Review":
                addEntry();
                break;
        }
    }
}


























//    private JLabel movieName;
//    private JLabel movieLength;
//    private JLabel genre;
//    private JLabel dateWatched;
//    private JLabel rating;
//    private JLabel userReview;

//    public void setReviewLabels() {
//        movieName = new JLabel("Movie name:");
//        movieLength = new JLabel("Movie length:");
//        genre = new JLabel("Genre:");
//        dateWatched = new JLabel("Date of viewing:");
//        rating = new JLabel("Your rating [1-5]:");
//        userReview = new JLabel("Your review:");
//    }
//
//    public void setReviewTextFields() {
//        movieNameText = new JTextField();
//        movieLengthText = new JTextField();
//        genreText = new JTextField();
//        dateWatchedText = new JTextField();
//        ratingText = new JTextField();
//        userReviewText = new JTextField();
//    }
//
//    public void setReviewLayout() {
//        add(title);
//        add(new JLabel(""));
//        add(movieName);
//        add(movieNameText);
//        add(movieLength);
//        add(movieLengthText);
//        add(genre);
//        add(genreText);
//        add(dateWatched);
//        add(dateWatchedText);
//        add(rating);
//        add(ratingText);
//        add(userReview);
//        add(userReviewText);
//    }