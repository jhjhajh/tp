package seedu.connoisseur.parser;

import seedu.connoisseur.commands.Commands;

/**
 * Handles Connoisseur's commands.
 */
public class Parser {
    private static Commands commands;

    /**
     * Constructor for parser class.
     */
    public Parser(Commands commands) {
        Parser.commands = commands;
    }

    /**
     * Processes user input and executes the relevant commands.
     *
     * @return true if exit command, false otherwise
     */
    public boolean determineCommand(String input) {
        String command = input.split(" ", 2)[0].toLowerCase().trim();
        String arguments;
        try {
            arguments = input.split(" ", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            arguments = null;
        }

        switch (command) {
        case "review":
            commands.reviewMode();
            break;
        case "reco":
            commands.recommendationMode();
            break;
        case "list":
            commands.list(arguments);
            break;
        case "edit":
            commands.edit(arguments);
            break;
        case "sort":
            commands.sort(arguments);
            break;
        case "new":
        case "add":
            commands.add(arguments);
            break;
        case "delete":
            commands.delete(arguments);
            break;
        case "done":
            commands.done(arguments);
            break;
        case "view":
            commands.view(arguments);
            break;
        case "help":
            commands.printHelp(arguments);
            break;
        case "exit":
        case "bye":
            commands.exit();
            return true;
        default:
            commands.invalidCommand();
        }
        return false;
    }
}
