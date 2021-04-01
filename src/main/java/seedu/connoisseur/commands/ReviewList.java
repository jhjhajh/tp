package seedu.connoisseur.commands;

import seedu.connoisseur.review.Review;
import seedu.connoisseur.sorter.SortMethod;
import seedu.connoisseur.sorter.Sorter;
import seedu.connoisseur.storage.ConnoisseurData;
import seedu.connoisseur.ui.Ui;

import java.util.ArrayList;

import static seedu.connoisseur.messages.Messages.INVALID_VIEW_TITLE;
import static seedu.connoisseur.messages.Messages.MISSING_VIEW_TITLE;
import static seedu.connoisseur.messages.Messages.INVALID_COMMAND;
import static seedu.connoisseur.messages.Messages.INVALID_DELETE_REVIEW_TITLE;
import static seedu.connoisseur.messages.Messages.INVALID_SORT_METHOD;
import static seedu.connoisseur.messages.Messages.CURRENT_SORT_METHOD;
import static seedu.connoisseur.messages.Messages.AVAILABLE_SORT_METHODS;
import static seedu.connoisseur.messages.Messages.SORT_METHOD_PROMPT;
import static seedu.connoisseur.messages.Messages.SORT_METHOD_SUCCESS;
import static seedu.connoisseur.messages.Messages.QUICK_PROMPT;
import static seedu.connoisseur.messages.Messages.REVIEW_TITLE_PROMPT;
import static seedu.connoisseur.messages.Messages.CATEGORY_PROMPT;
import static seedu.connoisseur.messages.Messages.DELETE_SUCCESS;
import static seedu.connoisseur.messages.Messages.RATING_PROMPT;
import static seedu.connoisseur.messages.Messages.ADD_SUCCESS;
import static seedu.connoisseur.messages.Messages.DESCRIPTION_PROMPT;
import static seedu.connoisseur.messages.Messages.MISSING_DELETE_TITLE;
import static seedu.connoisseur.messages.Messages.MISSING_EDIT_TITLE;
import static seedu.connoisseur.messages.Messages.EDIT_PROMPT_REVIEW;
import static seedu.connoisseur.messages.Messages.ANYTHING_ELSE;
import static seedu.connoisseur.messages.Messages.EDIT_DATE_PROMPT;
import static seedu.connoisseur.messages.Messages.EDIT_TITLE_PROMPT;
import static seedu.connoisseur.messages.Messages.EDIT_RATING_PROMPT;
import static seedu.connoisseur.messages.Messages.EDIT_CATEGORY_PROMPT;
import static seedu.connoisseur.messages.Messages.ENTER_DETAILS_PROMPT;
import static seedu.connoisseur.messages.Messages.DUPLICATE_REVIEW;

/**
 * Class with methods for different commands in review mode.
 */
public class ReviewList {
    public ArrayList<Review> reviews = new ArrayList<>();
    private final Sorter sorter;
    private final Ui ui;

    /**
     * Constructor for ReviewList with stored data. 
     * @param connoisseurData locally stored data
     * @param ui instance of ui for user interaction
     */
    public ReviewList(ConnoisseurData connoisseurData, Ui ui) {
        this.ui = ui;
        this.reviews = connoisseurData.getReviews();
        sorter = new Sorter(Sorter.stringToSortMethod(connoisseurData.getSortMethod()));
    }

    /**
     * Constructor for ReviewList without stored data. 
     * @param ui instance of ui for user interaction
     */
    public ReviewList(Ui ui) {
        this.ui = ui;
        sorter = new Sorter(SortMethod.LATEST);
    }

    /**
     * Add a review.
     *
     * @param input quick or full review
     */
    public void addReview(String input) {
        if (input == null || input.isBlank()) {
            ui.println(QUICK_PROMPT);
            switch (ui.readCommand().toLowerCase()) {
            case "y":
                addQuickReview();
                break;
            case "n":
                addFullReview();
                break;
            default:
                ui.println(INVALID_COMMAND);
            }
        } else {
            switch (input) {
            case "quick":
                addQuickReview();
                break;
            case "full":
                addFullReview();
                break;
            default:
                ui.println(INVALID_COMMAND);
            }
        }
    }

    /**
     * Add a quick review.
     */
    public void addQuickReview() {
        String title;
        String category;
        int rating;
        while (true) {
            ui.println(REVIEW_TITLE_PROMPT);
            title = ui.readCommand();
            if (checkAndPrintDuplicateReview(title)) {
                ui.printNoUniqueTitleMessage();
                continue;
            }
            if (title.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(CATEGORY_PROMPT);
            category = ui.readCommand().toLowerCase();
            if (category.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(RATING_PROMPT);
            try {
                rating = Integer.parseInt(ui.readCommand());
            } catch (NumberFormatException e) {
                ui.printInvalidRatingMessage();
                continue;
            }
            if (rating < 0 || rating > 5) {
                ui.printInvalidRatingMessage();
                continue;
            }
            assert rating >= 0 && rating <= 5 : "rating should be between 0 and 5";
            break;
        }
        String description = "No description entered.";
        Review r = new Review(title, category, rating, description);
        reviews.add(r);
        ui.println(title + ADD_SUCCESS);
    }

    /**
     * Add a full review.
     */
    public void addFullReview() {
        String title;
        String category;
        int rating;
        String description;
        while (true) {
            ui.println(REVIEW_TITLE_PROMPT);
            title = ui.readCommand();
            if (checkAndPrintDuplicateReview(title)) {
                ui.printNoUniqueTitleMessage();
                continue;
            }
            if (title.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(CATEGORY_PROMPT);
            category = ui.readCommand().toLowerCase();
            if (category.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            break;
        }
        while (true) {
            ui.println(RATING_PROMPT);
            try {
                rating = Integer.parseInt(ui.readCommand());
            } catch (NumberFormatException e) {
                ui.printInvalidRatingMessage();
                continue;
            }
            if (rating < 0 || rating > 5) {
                ui.printInvalidRatingMessage();
                continue;
            }
            assert rating >= 0 && rating <= 5 : "rating should be between 0 and 5";
            break;
        }
        while (true) {
            ui.println(DESCRIPTION_PROMPT);
            description = ui.readCommand();
            if (description.isBlank()) {
                ui.printEmptyInputMessage();
                continue;
            }
            break;
        }
        Review r = new Review(title, category, rating, description);
        reviews.add(r);
        ui.println(title + ADD_SUCCESS);
    }

    /**
     * Displays the sorted reviews.
     */
    public void displayReviews(ArrayList<Review> reviews) {
        ui.printReviewListHeading();
        for (int i = 0; i < reviews.size(); i++) {
            Review currentReview = reviews.get(i);
            ui.print("| " + (i + 1) + ". ");
            if (i < 9) {
                ui.print(" ");
            }
            ui.print(currentReview.getTitle());
            ui.printWhiteSpaceTitle(currentReview.getTitle().length());
            ui.print("| " + currentReview.getCategory());
            ui.printWhiteSpace(currentReview.getCategory().length());
            ui.print("| " + currentReview.starRating());
            ui.printWhiteSpace(currentReview.starRating().length());
            ui.print("| " + currentReview.getDateTime());
            ui.printWhiteSpaceDate(currentReview.getDateTime().length());
            ui.println("|");
        }
        ui.printTableEndBorderForReview();
    }

    
    /**
     * Displays a single review.
     *
     * @param currentReview review to be viewed
     */
    public void displaySingleReview(Review currentReview) {
        ui.println("+---------------------+--------------------------------------------------------------------+");
        ui.print("|Title                : " + currentReview.getTitle());
        ui.printWhiteSpaceView(currentReview.getTitle().length());
        ui.println("|");
        ui.print("|Category             : " + currentReview.getCategory());
        ui.printWhiteSpaceView(currentReview.getCategory().length());
        ui.println("|");
        ui.print("|Date & Time of Entry : " + currentReview.getDateTime());
        ui.printWhiteSpaceView(currentReview.getDateTime().length());
        ui.println("|");
        ui.print("|Rating               : " + currentReview.starRating());
        ui.printWhiteSpaceView(currentReview.starRating().length());
        ui.println("|");
        ui.print("|Description          : " + currentReview.getDescription());
        ui.printWhiteSpaceView(currentReview.getDescription().length());
        ui.println("|");
        ui.println("+---------------------+--------------------------------------------------------------------+");
    }

    /**
     * View a selected review.
     *
     * @param title title of the review to be viewed
     */
    public int viewReview(String title) {
        if (title == null || title.isBlank()) {
            ui.println(MISSING_VIEW_TITLE);
            return -1;
        }
        assert title != null : "title should not be empty";
        int reviewIndex = -1;
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getTitle().equals(title)) {
                reviewIndex = i;
                break;
            }
        }
        if (reviewIndex == -1) {
            ui.println(INVALID_VIEW_TITLE);
        } else {
            ui.println("Found a matching title: ");
            Review currentReview = reviews.get(reviewIndex);
            displaySingleReview(currentReview);
        }
        return reviewIndex;
    }

    /**
     * List reviews according to different types of input.
     *
     * @param sortMethod is listing method preferred by user. If there is no
     *                   preferred listing method, default listing will be used.
     */
    public void listReviews(String sortMethod) {
        if (reviews.size() == 0) {
            ui.printEmptyReviewListMessage();
        } else if (!validSortMethod(sortMethod)) {
            ui.printInvalidSortMethodMessage();
        } else {
            if (sortMethod == null) {
                sorter.sortReview(reviews);
                displayReviews(reviews);
            } else {
                try {
                    sorter.sortReview(reviews, sortMethod);
                    displayReviews(reviews);
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.printInvalidSortMethodMessage();
                }
            }
        }
    }

    /**
     * Check if input sort method by user is valid.
     *
     * @param sortMethod is the preferred sort method input by user.
     */
    private boolean validSortMethod(String sortMethod) {
        ArrayList<String> validSortMethods = new ArrayList<String>();
        validSortMethods.add("rating");
        validSortMethods.add("category");
        validSortMethods.add("title");
        validSortMethods.add("earliest");
        validSortMethods.add("latest");
        validSortMethods.add(null);
        return validSortMethods.contains(sortMethod);
    }

    /**
     * Sort a review based on input sort type.
     *
     * @param sortType sorting method to be used
     */
    public void sortReview(String sortType) {
        if (sortType == null || sortType.isBlank()) {
            String sortMethod = sorter.getSortMethod();
            ui.println(CURRENT_SORT_METHOD + sortMethod.toUpperCase());
            ui.println(AVAILABLE_SORT_METHODS);
            ui.println(SORT_METHOD_PROMPT);
        } else if (validSortMethod(sortType)) {
            sorter.changeSortMethod(sortType);
            ui.println(SORT_METHOD_SUCCESS + sortType.toUpperCase());
        } else {
            ui.println(sortType + INVALID_SORT_METHOD);
        }
    }

    /**
     * Edit a review. 
     * @param title title of review to be edited
     */
    public void editReview(String title) {
        if (title == null || title.isBlank()) {
            ui.println(MISSING_EDIT_TITLE);
        } else {
            int index = viewReview(title);
            if (index == -1) {
                return;
            }
            boolean isDoneEditing = false;
            while (!isDoneEditing) {
                do {
                    ui.println(EDIT_PROMPT_REVIEW);
                } while (!editReviewFields(index));
                while (true) {
                    ui.println(ANYTHING_ELSE);
                    String answer = ui.readCommand();
                    switch (answer.toLowerCase()) {
                    case "y":
                        break;
                    case "n":
                        isDoneEditing = true;
                        break;
                    default:
                        ui.println(INVALID_COMMAND);
                        continue;
                    }
                    break;
                }
            }
            while (true) {
                ui.println(EDIT_DATE_PROMPT);
                String answer = ui.readCommand();
                switch (answer) {
                case "y":
                    Review currentReview = reviews.get(index);
                    currentReview.setDateAndTimeOfEntry();
                    break;
                case "n":
                    break;
                default:
                    ui.println(INVALID_COMMAND);
                    continue;
                }
                break;
            }
        }
    }

    /**
     * Edit specific fields of the review.
     * @param index index of the review to be edited
     */
    public boolean editReviewFields(int index) {
        String input = ui.readCommand();
        try {
            input = input.trim().toLowerCase();
        } catch (ArrayIndexOutOfBoundsException e) {
            input = null;
        }
        switch (input) {
        case "title":
            String newTitle;
            while (true) {
                ui.println(EDIT_TITLE_PROMPT);
                newTitle = ui.readCommand();
                if (newTitle.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                break;
            }
            reviews.get(index).setTitle(newTitle);
            break;
        case "rating":
            int newRating;
            while (true) {
                ui.println(EDIT_RATING_PROMPT);
                try {
                    newRating = Integer.parseInt(ui.readCommand());
                } catch (NumberFormatException e) {
                    ui.printInvalidRatingMessage();
                    continue;
                }
                if (newRating < 0 || newRating > 5) {
                    ui.printInvalidRatingMessage();
                    continue;
                }
                break;
            }
            reviews.get(index).setRating(newRating);
            break;
        case "description":
            String newDescription;
            while (true) {
                ui.println(ENTER_DETAILS_PROMPT);
                newDescription = ui.readCommand();
                if (newDescription.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                break;
            }
            reviews.get(index).setDescription(newDescription);
            break;
        case "category":
            String newCategory;
            while (true) {
                ui.println(EDIT_CATEGORY_PROMPT);
                newCategory = ui.readCommand();
                if (newCategory.isBlank()) {
                    ui.printEmptyInputMessage();
                    continue;
                }
                break;
            }
            reviews.get(index).setCategory(newCategory);
            break;
        default:
            ui.println(INVALID_COMMAND);
            return false;
        }
        return true;
    }

    /**
     * Delete review.
     */
    public void deleteReview(String title) {
        if (title == null || title.isBlank()) {
            ui.println(MISSING_DELETE_TITLE);
            return;
        }
        int reviewIndex = -1;
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getTitle().compareTo(title) == 0) {
                reviewIndex = i;
                break;
            }
        }
        if (reviewIndex == -1) {
            ui.println(INVALID_DELETE_REVIEW_TITLE);
        } else {
            reviews.remove(reviewIndex);
            ui.println(title + DELETE_SUCCESS);
        }
    }

    /**
     * Check for duplicate review titles in existing review list.
     *
     * @param title review title input by user.
     * @return true if there is a duplicate, and false if there are no duplicates.
     */
    public boolean checkAndPrintDuplicateReview(String title) {
        int reviewIndex = -1;
        for (int i = 0; i < reviews.size(); i++) {
            if ((reviews.get(i).getTitle().toLowerCase()).compareTo(title.toLowerCase()) == 0) {
                reviewIndex = i;
            }
        }
        if (reviewIndex == -1) {
            return false;
        }
        ui.println(DUPLICATE_REVIEW);
        displaySingleReview(reviews.get(reviewIndex));
        return true;
    }

    /**
     * Add converted recommendation to reviews. 
     * @param r converted recommendation
     */
    public void receiveConvert(Review r) {
        reviews.add(r);
    }
}