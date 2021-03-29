package seedu.connoisseur.commandlist;

import seedu.connoisseur.exceptions.DuplicateException;
import seedu.connoisseur.review.Review;
import seedu.connoisseur.sorter.SortMethod;
import seedu.connoisseur.sorter.Sorter;
import seedu.connoisseur.storage.ConnoisseurData;
import seedu.connoisseur.storage.Storage;
import seedu.connoisseur.ui.Ui;

import java.util.ArrayList;

import static seedu.connoisseur.messages.Messages.*;

/**
 * Class with methods for different commands in review mode.
 */
public class ReviewCommandList {
    public ArrayList<Review> reviewList = new ArrayList<>();
    private final Sorter sorter;
    private final Ui ui;

    public ReviewCommandList(ConnoisseurData connoisseurData, Ui ui, Storage storage) {
        this.ui = ui;
        this.reviewList = connoisseurData.getReviewList();
        sorter = new Sorter(SortMethod.LATEST);
    }

    public ReviewCommandList(Ui ui, Storage storage) {
        this.ui = ui;
        sorter = new Sorter(SortMethod.LATEST);
    }

    /**
     * Prints the sorted reviews.
     */
    public void printReviews(ArrayList<Review> reviewList) {
        ui.printReviewListHeading();
        for (int i = 0; i < reviewList.size(); i++) {
            Review currentReview = reviewList.get(i);
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
            ui.println("| " + currentReview.getDateTime() + "   |");
        }
        ui.printTableEndBorderForReview();
    }

    /**
     * View a selected review.
     *
     * @param title title of the review to be viewed
     */
    public int viewReviewCommand(String title) {
        if (title == null || title.isBlank()) {
            ui.println(MISSING_VIEW_TITLE);
            return -1;
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
            Review currentReview = reviewList.get(reviewIndex);
            ui.printView(currentReview);
        }
        return reviewIndex;
    }

    /**
     * List reviews according to different types of input.
     *
     * @param sortMethod is listing method preferred by user. If there is no
     *              preferred listing method, default listing will be used.
     */
    public void listReviews(String sortMethod, ArrayList<Review> reviewList) {
        if (reviewList.size() == 0) {
            ui.printEmptyReviewListMessage();
        } else if (!validSortMethod(sortMethod)) {
            ui.printInvalidSortMethodMessage();
        } else {
            if (sortMethod == null) {
                sorter.sortReview(reviewList);
                printReviews(reviewList);
            } else {
                try {
                    sorter.sortReview(reviewList, sortMethod);
                    printReviews(reviewList);
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
            ui.println(SORT_METHOD_PROMPT);
            return;
        }
        if (sortType.equals("title") || sortType.equals("earliest")
                || sortType.equals("latest") || sortType.equals("rating") || sortType.equals("category")) {
            sorter.changeSortMethod(sortType);
            ui.println(SORT_METHOD_SUCCESS + sortType.toUpperCase());
        } else {
            ui.println(sortType + INVALID_SORT_METHOD);
        }
    }

    public void editReview(String title, ArrayList<Review> reviewList) {
        if (title == null || title.isBlank()) {
            ui.println(MISSING_EDIT_TITLE);
        } else {
            int index = viewReviewCommand(title);
            if (index == -1) {
                return;
            }
            boolean isDoneEditing = false;
            do {
                ui.println("What would you like to edit (Title / Category / Rating / Description)?");
                determineEditCommand(index);
                ui.println("Would you like to edit anything else (y/n)?");
                String answer = ui.readCommand();
                switch (answer.toLowerCase()) {
                case "y":
                    break;
                case "n":
                    isDoneEditing = true;
                    break;
                default:
                    ui.println(INVALID_COMMAND);
                    isDoneEditing = true;
                }
            } while (!isDoneEditing);
            ui.println("Would You like to update the date of entry for the changes made(y/n)?");
            String answer = ui.readCommand();
            switch (answer) {
            case "y":
                Review currentReview = reviewList.get(index);
                currentReview.setDateAndTimeOfEntry();
                break;
            case "n":
                break;
            default:
                ui.println(INVALID_COMMAND);
                break;
            }
        }
    }

    public void determineEditCommand(int index) {
        Ui ui = new Ui();
        String input = ui.readCommand();
        try {
            input = input.trim().toLowerCase();
        } catch (ArrayIndexOutOfBoundsException e) {
            input = null;
        }
        switch (input) {
        case "title":
            System.out.println("What would you like to change the title to?");
            String newTitle = ui.readCommand();
            editReviewTitle(newTitle, index);
            break;
        case "rating":
            System.out.println("What would you like to change the rating to out of 5 stars?");
            String newRating = ui.readCommand();
            try {
                if (Integer.parseInt(newRating) <= 5 && Integer.parseInt(newRating) >= 0) {
                    editReviewRating(newRating, index);
                } else {
                    System.out.println("Invalid rating, failed to edit rating ");
                }
            } catch (NumberFormatException ne) {
                System.out.println("Invalid rating, failed to edit rating ");
            }
            break;
        case "description":
            System.out.println("Enter your new description of the review: ");
            String newDescription = ui.readCommand();
            editReviewDescription(newDescription, index);
            break;
        case "category":
            System.out.println("What would you like to change the category to?");
            String newCategory = ui.readCommand();
            editReviewCategory(newCategory, index);
            break;
        default:
            ui.println(INVALID_COMMAND);
            break;
        }
    }

    public void editReviewTitle(String newTitle, int index) {
        Review currentReview = reviewList.get(index);
        currentReview.setTitle(newTitle);
    }

    public void editReviewRating(String newRating, int index) {
        Review currentReview = reviewList.get(index);
        currentReview.setRating(Integer.parseInt(newRating));
    }

    public void editReviewDescription(String newDescription, int index) {
        Review currentReview = reviewList.get(index);
        currentReview.setDescription(newDescription);
    }

    public void editReviewCategory(String newCategory, int index) {
        Review currentReview = reviewList.get(index);
        currentReview.setCategory(newCategory);
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
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getTitle().compareTo(title) == 0) {
                reviewIndex = i;
                break;
            }
        }
        if (reviewIndex == -1) {
            ui.println(INVALID_DELETE_REVIEW_TITLE);
        } else {
            reviewList.remove(reviewIndex);
            ui.println(title + DELETE_SUCCESS);
        }
    }

    /**
     * Add a review.
     *
     * @param input quick or long review
     */
    public void addReview(String input) {
        if (input == null || input.isBlank()) {
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
        isDuplicate = checkAndPrintDuplicateReview(title);
        if (isDuplicate) {
            throw new DuplicateException();
        }
        ui.println(CATEGORY_PROMPT);
        String category = ui.readCommand().toLowerCase();
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
        isDuplicate = checkAndPrintDuplicateReview(title);
        if (isDuplicate) {
            throw new DuplicateException();
        }
        ui.println(CATEGORY_PROMPT);
        String category = ui.readCommand().toLowerCase();
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
     * Check for duplicate review titles in existing review list.
     *
     * @param title review title input by user.
     * @return true if there is a duplicate, and false if there are no duplicates.
     */
    public boolean checkAndPrintDuplicateReview(String title) {
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