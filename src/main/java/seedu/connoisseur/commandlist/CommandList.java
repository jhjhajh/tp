package seedu.connoisseur.commandlist;

import seedu.connoisseur.exceptions.DuplicateException;
import seedu.connoisseur.review.Review;
import seedu.connoisseur.storage.Storage;
import seedu.connoisseur.ui.Ui;
import seedu.connoisseur.sorter.SortMethod;
import seedu.connoisseur.sorter.Sorter;

import java.util.ArrayList;

import static seedu.connoisseur.messages.Messages.INVALID_VIEW_TITLE;
import static seedu.connoisseur.messages.Messages.MISSING_VIEW_TITLE;
import static seedu.connoisseur.messages.Messages.INVALID_COMMAND;
import static seedu.connoisseur.messages.Messages.INVALID_DELETE_TITLE;
import static seedu.connoisseur.messages.Messages.INVALID_SORT_METHOD;
import static seedu.connoisseur.messages.Messages.MISSING_SORT_METHOD;
import static seedu.connoisseur.messages.Messages.SORT_METHOD_SUCCESS;
import static seedu.connoisseur.messages.Messages.QUICK_PROMPT;
import static seedu.connoisseur.messages.Messages.TITLE_PROMPT;
import static seedu.connoisseur.messages.Messages.CATEGORY_PROMPT;
import static seedu.connoisseur.messages.Messages.DELETE_SUCCESS;
import static seedu.connoisseur.messages.Messages.RATING_PROMPT;
import static seedu.connoisseur.messages.Messages.ADD_SUCCESS;
import static seedu.connoisseur.messages.Messages.DESCRIPTION_PROMPT;
import static seedu.connoisseur.messages.Messages.MISSING_DELETE_TITLE;


/**
 * Class with methods for different commands.
 */
public class CommandList {

    public ArrayList<Review> reviewList;
    private Sorter sorter;
    private Ui ui;
    private Storage storage;

    /**
     * Creates tasks according to user data from files.
     *
     * @param dataReviews List of tasks from user connoisseur.txt file.
     */
    public CommandList(ArrayList<String> dataReviews, Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        reviewList = new ArrayList<Review>();
        for (String review : dataReviews) {
            if (review.length() == 0) {
                continue;
            }
            reviewList.add(Review.textToReview(review));
        }
        sorter = new Sorter(SortMethod.DATE_LATEST);
    }

    /**
     * Creates new tasks if no existing data in files.
     */
    public CommandList(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        reviewList = new ArrayList<Review>();
        sorter = new Sorter(SortMethod.DATE_LATEST);
    }

    /**
     * List reviews according to different types of input.
     *
     * @param sortMethod is the listing method preferred by user. If there is no
     *                   preferred listing method, default listing will be used.
     */
    public void listReviews(String sortMethod) {
        if (reviewList.size() == 0) {
            ui.printEmptyCommandListMessage();
        } else {
            if (sortMethod == null) {
                sorter.sort(reviewList);
                printReviews(reviewList);
            } else {
                try {
                    sorter.sort(reviewList, sortMethod);
                    printReviews(reviewList);
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.printInvalidSortMethodMessage();
                }
            }
        }
    }

    /**
     * Prints the sorted reviews.
     */
    public void printReviews(ArrayList<Review> reviewList) {
        ui.printListHeading();
        for (int i = 0; i < reviewList.size(); i++) {
            Review currentReview = reviewList.get(i);
            ui.print((i + 1) + ". ");
            if (i < 9) {
                ui.print(" ");
            }
            ui.print(currentReview.getTitle());
            ui.printWhiteSpace(currentReview.getTitle().length());
            ui.print(currentReview.getCategory());
            ui.printWhiteSpace(currentReview.getCategory().length());
            ui.print(currentReview.starRating());
            ui.printWhiteSpace(currentReview.starRating().length());
            ui.println(currentReview.getDateTime());
        }
    }

    /**
     * Sort a review based on input sort type.
     *
     * @param sortType sorting method to be used
     */
    public void sortReview(String sortType) {
        if (sortType == null) {
            ui.println(MISSING_SORT_METHOD);
            return;
        }
        if (sortType.equals("title") || sortType.equals("date earliest")
                || sortType.equals("date latest") || sortType.equals("rating") || sortType.equals("category")) {
            sorter.changeSortMethod(sortType);
            ui.println(SORT_METHOD_SUCCESS + sortType.toUpperCase());
        } else {
            ui.println(sortType + INVALID_SORT_METHOD);
        }
    }

    /**
     * Add a review.
     *
     * @param input quick or long review
     */
    public void addReview(String input) {
        if (input == null) {
            ui.println(QUICK_PROMPT);
            switch (ui.readCommand().toLowerCase()) {
            case "y":
                try {
                    addQuickReview();
                } catch (DuplicateException de) {
                    System.out.println("Please try again with a unique title instead!");
                }
                break;
            case "n":
                try {
                    addLongReview();
                } catch (DuplicateException de) {
                    System.out.println("Please try again with a unique title instead!");
                }
                break;
            default:
                ui.println(INVALID_COMMAND);
            }
        } else {
            switch (input) {
            case "quick":
                try {
                    addQuickReview();
                } catch (DuplicateException de) {
                    System.out.println("Please try again with a unique title instead!");
                }
                break;
            case "long":
                try {
                    addLongReview();
                } catch (DuplicateException de) {
                    System.out.println("Please try again with a unique title instead!");
                }
                break;
            default:
                ui.println(INVALID_COMMAND);
            }
        }
    }

    /**
     * Add a quick review.
     */
    public void addQuickReview() throws DuplicateException {
        boolean isDuplicate;
        String description = "No description entered. ";
        ui.println(TITLE_PROMPT);
        String title = ui.readCommand();
        isDuplicate = checkAndPrintDuplicate(title);
        if (isDuplicate) {
            throw new DuplicateException();
        }
        ui.println(CATEGORY_PROMPT);
        String category = ui.readCommand();
        ui.println(RATING_PROMPT);
        try {
            int rating = Integer.parseInt(ui.readCommand());
            if (rating < 0 || rating > 5) {
                ui.printInvalidRatingMessage();
                return;
            }
            assert rating >= 0 && rating <= 5 : "rating should be between 0 and 5";
            Review r = new Review(title, category, rating, description);
            reviewList.add(r);
            ui.println(title + ADD_SUCCESS);
        } catch (NumberFormatException e) {
            ui.printInvalidRatingMessage();
        }
    }

    /**
     * Add a long review.
     */
    public void addLongReview() throws DuplicateException {
        boolean isDuplicate;
        ui.println(TITLE_PROMPT);
        String title = ui.readCommand();
        isDuplicate = checkAndPrintDuplicate(title);
        if (isDuplicate) {
            throw new DuplicateException();
        }
        ui.println(CATEGORY_PROMPT);
        String category = ui.readCommand();
        ui.println(RATING_PROMPT);
        try {
            int rating = Integer.parseInt(ui.readCommand());
            if (rating < 0 || rating > 5) {
                ui.printInvalidRatingMessage();
                return;
            }
            assert rating >= 0 && rating <= 5 : "rating should be between 0 and 5";
            ui.println(DESCRIPTION_PROMPT);
            String description = ui.readCommand();
            Review r = new Review(title, category, rating, description);
            reviewList.add(r);
            ui.println(title + ADD_SUCCESS);
        } catch (NumberFormatException e) {
            ui.printInvalidRatingMessage();
        }
    }

    /**
     * Delete review.
     */
    public void deleteReview(String title) {
        if (title == null) {
            ui.println(MISSING_DELETE_TITLE);
            return;
        }
        int reviewIndex = -1;
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getTitle().compareTo(title) == 0) {
                reviewIndex = i;
                break;
            }
        }
        if (reviewIndex == -1) {
            ui.println(INVALID_DELETE_TITLE);
        } else {
            reviewList.remove(reviewIndex);
            ui.println(title + DELETE_SUCCESS);
        }
    }

    /**
     * Print text to help user with using the application.
     *
     * @param arguments is the type of help determined by user input.
     */
    public void printHelp(String arguments) {
        if (arguments == null) {
            ui.printGeneralHelpMessage();
        } else if (arguments.equals("sort")) {
            ui.printSortHelpMessage();
        } else if (arguments.equals("list")) {
            ui.printListHelpMessage();
        } else if (arguments.equals("review") || arguments.equals("new")) {
            ui.printReviewHelpMessage();
        } else if (arguments.equals("delete")) {
            ui.printDeleteHelpMessage();
        } else if (arguments.equals("view")) {
            ui.printViewHelpMessage();
        } else if (arguments.equals("exit")) {
            ui.printExitHelpMessage();
        } else {
            ui.printInvalidHelpMessage();
        }
    }

    /**
     * Print invalid command text.
     */
    public void invalidCommand() {
        ui.println(INVALID_COMMAND);
    }

    /**
     * Exits connoisseur.
     */
    public void exit() {
        storage.saveData(reviewList);
        ui.printExitMessage();
    }

    /**
     * View a selected review.
     *
     * @param title title of the review to be viewed
     */
    public void viewReview(String title) {
        if (title == null) {
            ui.println(MISSING_VIEW_TITLE);
            return;
        }
        assert title != null : "title should not be empty";
        int reviewIndex = -1;
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getTitle().compareTo(title) == 0) {
                reviewIndex = i;
                break;
            }
        }
        if (reviewIndex == -1) {
            ui.println(INVALID_VIEW_TITLE);
        } else {
            ui.println("Found a matching title: ");
            for (int i = 0; i < reviewList.size(); i++) {
                Review currentReview = reviewList.get(reviewIndex);
                ui.printView(currentReview);
            }
        }
    }

    public boolean checkAndPrintDuplicate(String title) {
        int reviewIndex = -1;
        for (int i = 0; i < reviewList.size(); i++) {
            if ((reviewList.get(i).getTitle().toLowerCase()).compareTo(title.toLowerCase()) == 0) {
                reviewIndex = i;
            }
        }
        if (reviewIndex != -1) {
            System.out.println("There is a review in your list with the same title: ");
            Review currentReview = reviewList.get(reviewIndex);
            ui.print((reviewList.indexOf(currentReview) + 1) + ". ");
            if (reviewList.indexOf(currentReview) < 9) {
                ui.print(" ");
            }
            ui.print(currentReview.getTitle());
            ui.printWhiteSpace(currentReview.getTitle().length());
            ui.print(currentReview.getCategory());
            ui.printWhiteSpace(currentReview.getCategory().length());
            ui.print(currentReview.starRating());
            ui.printWhiteSpace(currentReview.starRating().length());
            ui.println(currentReview.getDateTime());
            return true;
        } else {
            return false;
        }
    }
}
