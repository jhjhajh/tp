package seedu.connoisseur.commandlist;

import seedu.connoisseur.review.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import seedu.connoisseur.commandlist.CommandList;
import seedu.connoisseur.sorter.SortMethod;
import seedu.connoisseur.sorter.Sorter;
import seedu.connoisseur.storage.Storage;
import seedu.connoisseur.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
