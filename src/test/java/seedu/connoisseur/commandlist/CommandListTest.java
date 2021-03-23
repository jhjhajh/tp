package seedu.connoisseur.commandlist;

import seedu.connoisseur.review.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import seedu.connoisseur.commandlist.CommandList;
import seedu.connoisseur.sorter.SortMethod;
import seedu.connoisseur.sorter.Sorter;

import org.junit.After;
import seedu.connoisseur.review.Review;
import seedu.connoisseur.storage.Storage;
import seedu.connoisseur.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CommandListTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    public ArrayList<Review> reviewList;
    private Sorter sorter;
    private Ui ui;
    private Storage storage;

    public void CommandListTest (Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        reviewList = new ArrayList<Review>();
        sorter = new Sorter(SortMethod.DATE_LATEST);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void listReview_noReviewsExist() {

        setUpStreams();
        System.out.print("You have no reviews, type 'new' to start!");
        assertEquals("You have no reviews, type 'new' to start!", outContent.toString());
        restoreStreams();
    }

    @Test
    void checkAndPrintDuplicate() {
        CommandList commandList = new CommandList(new ArrayList<String>(), new Ui(), new Storage(new Ui()));
        commandList.reviewList = new ArrayList<Review>();
        assertFalse(commandList.checkAndPrintDuplicate("Avengers"));
    }

}
