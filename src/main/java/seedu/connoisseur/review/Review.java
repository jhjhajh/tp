package seedu.connoisseur.review;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    protected String title;
    protected String category;
    protected String dateAndTimeOfEntry;
    protected int rating;
    protected String description;
    public static int MAX_NUM_OF_STARS = 5;


    /**
     * Creates a review.
     *
     * @param title       title of the review
     * @param category    category of the experience
     * @param rating      rating of the experience
     * @param description description of the experience
     */
    public Review(String title, String category, int rating, String description) {
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.description = description;
        LocalDateTime dateTime = LocalDateTime.now();
        this.dateAndTimeOfEntry = dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
    }

    /**
     * Creates a review with specified date.
     *
     * @param title       title of the review
     * @param category    category of the experience
     * @param rating      rating of the experience
     * @param description description of the experience
     * @param date        date of review
     */
    public Review(String title, String category, int rating, String description, String date) {
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.description = description;
        this.dateAndTimeOfEntry = date;
    }

    /**
     * Gets the date of the review.
     *
     * @return date of entry as a string
     */
    public String getDateTime() {
        return dateAndTimeOfEntry;
    }

    /**
     * Set a new date of the review.
     */
    public void setDateAndTimeOfEntry() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.dateAndTimeOfEntry = dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
    }

    /**
     * Gets the title of the review.
     *
     * @return title of review
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the review.
     *
     * @param title new title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the category of the experience.
     *
     * @return category of the experience as a string
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the experience.
     *
     * @param category new category of experience
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the rating of the experience.
     *
     * @return rating of the experience as an integer
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the experience.
     *
     * @param rating new rating to be set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets the description of the experience.
     *
     * @return description of the experience as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the experience.
     *
     * @param description new description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Converts the rating to visual form.
     *
     * @return rating of the experience as a string
     */
    public String starRating() {
        String starRating = "";
        int stars = this.rating;
        assert stars >= 0 && stars <= 5 : "rating should be between 0 and 5";
        for (int i = 0; i < MAX_NUM_OF_STARS; i++) {
            if (stars > 0) {
                starRating = starRating.concat("★ ");
            } else {
                starRating = starRating.concat("✰ ");
            }
            stars--;
        }
        return starRating;
    }

    /**
     * Converts the review to a string.
     *
     * @return review as a string for display
     */
    public String toString() {
        return title + "      " + rating + "      " + dateAndTimeOfEntry;
    }

    /**
     * Converts a review into text for storage.
     *
     * @return review in a single string
     */
    public String reviewToText() {
        return getDateTime() + "|" + getTitle() + "|" + getCategory() + "|" + getRating() + "|" + getDescription();
    }

    /**
     * Converts a single string into a review.
     *
     * @param review the review saves as a single string
     * @return review as a Review object
     */
    public static Review textToReview(String review) {
        String[] reviewFields = review.split("\\|", 5);
        Review r = new Review(reviewFields[1], reviewFields[2], Integer.parseInt(reviewFields[3]),
                reviewFields[4], reviewFields[0]);
        return r;
    }
}
