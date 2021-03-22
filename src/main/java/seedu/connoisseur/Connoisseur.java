package seedu.connoisseur;

import seedu.connoisseur.commandlist.CommandList;
import seedu.connoisseur.parser.Parser;
import seedu.connoisseur.storage.Storage;
import seedu.connoisseur.ui.Ui;

import static seedu.connoisseur.messages.Messages.COMMAND_PROMPT;

public class Connoisseur {

    /**
     * Main entry-point for the java.connoisseur.Connoisseur application.
     */
    public static void main(String[] args) {
        assert false : "dummy assertion set to fail";
        new Connoisseur();
    }

    /**
     * Sets up required files for Connoisseur to start.
     */
    public Connoisseur() {
        Ui ui = new Ui(true);
        Storage storage = new Storage(ui);
        CommandList commandList;
        if (storage.retrieveTextFile()) {
            commandList = new CommandList(storage.loadData(), ui, storage);
        } else {
            commandList = new CommandList(ui, storage);
        }
        ui.printGreeting();
        String input;
        Parser parser = new Parser(commandList);
        boolean isExitCommand = false;

        while (!isExitCommand) {
            ui.println(COMMAND_PROMPT);
            input = ui.readCommand();
            isExitCommand = parser.determineCommand(input);
        }
    }
}


