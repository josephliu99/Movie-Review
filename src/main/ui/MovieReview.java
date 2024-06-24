package ui;

import model.Movie;
import model.Review;
import model.ReviewsList;
import model.exceptions.OutOfBoundException;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Console UI for MovieReview application
public class MovieReview {

    private static final String JSON_STORE = "./data/reviewslist.json";
    private Scanner input;
    private ReviewsList list;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: starts the movie review application
    public MovieReview() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMovieReviewApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user's prompt and runs the specific prompt accordingly
    private void runMovieReviewApp() {
        boolean running = true;
        String chooseMenu;

        input = new Scanner(System.in);
        input.useDelimiter("\n");
        createNewList();

        while (running) {
            mainMenu();

            chooseMenu = input.next();

            if (chooseMenu.equals("q")) {
                running = false;
            } else {
                proceedWithApp(chooseMenu);
            }
        }
    }



    // EFFECTS: main menu display
    private void mainMenu() {
        System.out.println("\nChoose from:");
        System.out.println("\n a -> create new movie review");
        System.out.println("\n b -> view list of movie reviewed");
        System.out.println("\n c -> view all reviews in detail");
        System.out.println("\n s -> save list to file");
        System.out.println(" \n l -> load list of reviews from file");
        System.out.println("\n q -> quit\n");
        System.out.println("----------------------------------------");
        System.out.print("Enter Choice: ");
    }

    // MODIFIES: this
    // EFFECTS: process user's input
    private void proceedWithApp(String chooseMenu) {
        if (chooseMenu.equals("a")) {
            createNewReview();
        } else if (chooseMenu.equals("b")) {
            viewMovieList();
        } else if (chooseMenu.equals("c")) {
            viewReviews();
        } else if (chooseMenu.equals("s")) {
            saveList();
        } else if (chooseMenu.equals("l")) {
            loadList();
        } else {
            System.out.println("Input not valid, please enter again.");
        }
    }

    private void createNewList() {
        list = new ReviewsList();
    }



    // MODIFIES: this
    // EFFECTS: create a new review entry
    private void createNewReview() {


        System.out.print("\nEnter movie name: ");
        String movieTitle = input.next().trim();

        System.out.print("\nEnter genre: ");
        String movieGenre = input.next().trim();

        System.out.print("\nEnter movie length: ");
        int movieLength = input.nextInt();

        Movie movie = new Movie(movieTitle, movieGenre, movieLength);

        Review newReview = new Review(movie);

        System.out.print("\n[m/d/y]Enter date watched: ");
        String dateWatched = input.next().trim();
        newReview.setDate(dateWatched);

        rate(newReview);

        System.out.println("\nEnter review: ");
        String review = input.next();
        newReview.setReview(review);

        printEntry(newReview);
        list.addEntry(newReview);

    }

    // MODIFIES: this
    // EFFECTS: rating is added to the review entry
    private void rate(Review newReview) {
        System.out.print("\n[0-5]Enter Rating: ");
        int rating = input.nextInt();

        try {
            newReview.setRating(rating);
        } catch (OutOfBoundException e) {
            System.out.println("Invalid input. Please rate the movie from 0 to 5.");
            rate(newReview);
        }

    }

    // EFFECTS: print the review entry
    private void printEntry(Review review) {
        System.out.println(review);
    }



    // EFFECTS: View a list of movie titles
    private void viewMovieList() {
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.get(i).getMovie().getTitle());
        }
    }

    // EFFECTS: Print all the reviews
    private void viewReviews() {
        List<Review> reviews = list.getReviews();

        for (Review r : reviews) {
            System.out.println(r);
        }
    }


    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            System.out.println("The file is saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadList() {
        try {
            list = jsonReader.read();
            System.out.println("The list is loaded");
        } catch (IOException | OutOfBoundException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
